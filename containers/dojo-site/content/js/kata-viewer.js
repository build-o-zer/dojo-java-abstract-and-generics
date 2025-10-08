/**
 * GitKataViewer - Java Abstraction Kata Viewer
 * Handles loading, displaying, and enhancing kata content with syntax highlighting,
 * Mermaid diagrams, dialogue styling, and text-to-speech functionality.
 */
class GitKataViewer {
    constructor() {
        this.katas = [];
        this.currentViewer = null;
        
        // Make this instance globally available for external access
        window.kataViewer = this;
    }
    
    async initialize() {
        await this.init();
        return this;
    }
    
    reprocessCodeBlocks() {
        console.log('reprocessCodeBlocks called');
        if (this.currentViewer) {
            console.log('Current viewer available, applying highlighting...');
            this.applyCustomHighlighting(this.currentViewer);
        } else {
            console.log('No current viewer available');
        }
    }
    
    async init() {
        await this.loadKatas();
        this.renderKataList();
        this.setupEventListeners();
        
        // Configure marked without custom highlighting initially
        marked.setOptions({
            breaks: true,
            gfm: true
        });
    }
    
    applyCustomHighlighting(viewer) {
        console.log('applyCustomHighlighting called');
        const codeBlocks = viewer.querySelectorAll('pre code');
        console.log(`Found ${codeBlocks.length} code blocks`);
        
        // Check if Prism is available
        if (typeof Prism === 'undefined') {
            console.warn('Prism not available, falling back to plain text');
            for (const block of codeBlocks) {
                if (!block.innerHTML.includes('prism-line')) {
                    const code = block.textContent;
                    block.innerHTML = this.addLineNumbers(code);
                }
            }
            return;
        }
        
        console.log('Prism available, processing code blocks...');
        
        for (const block of codeBlocks) {
            // Skip Mermaid blocks
            if (block.classList.contains('language-mermaid') ||
                block.textContent.trim().startsWith('graph ') ||
                block.textContent.trim().startsWith('classDiagram') ||
                block.textContent.trim().startsWith('sequenceDiagram')) {
                console.log('Skipping Mermaid block');
                continue;
            }
            
            // Skip if already processed
            if (block.innerHTML.includes('prism-line')) {
                console.log('Skipping already processed block');
                continue;
            }
            
            const code = block.textContent;
            const language = this.detectLanguage(block);
            console.log(`Processing code block with language: ${language}`);
            
            try {
                // Add language class to the block for Prism
                block.className = `language-${language}`;
                
                // Use Prism to highlight the code
                const highlightedHTML = Prism.highlight(code, Prism.languages[language] || Prism.languages.java, language);
                
                console.log('Prism highlighting successful');
                
                // Apply line numbers
                const finalHTML = this.addLineNumbers(highlightedHTML);
                
                block.innerHTML = finalHTML;
                console.log('Code block processed successfully');
            } catch (err) {
                console.warn(`Failed to highlight code with language '${language}':`, err);
                // Fallback to plain text with line numbers
                block.innerHTML = this.addLineNumbers(code);
            }
        }
        
        console.log('Finished processing all code blocks');
    }
    
    detectLanguage(block) {
        // Check class names for language
        const classList = Array.from(block.classList);
        for (const className of classList) {
            if (className.startsWith('language-')) {
                return className.replace('language-', '');
            }
        }
        
        // Default to java for code blocks without explicit language
        return 'java';
    }
    
    addLineNumbers(highlightedCode) {
        // Check if line numbers are already present
        if (highlightedCode.includes('prism-line-number')) {
            return highlightedCode;
        }
        
        // Check if this is Mermaid code by looking for common Mermaid keywords
        const trimmedCode = highlightedCode.trim();
        if (trimmedCode.startsWith('graph ') || 
            trimmedCode.startsWith('classDiagram') || 
            trimmedCode.startsWith('sequenceDiagram') ||
            trimmedCode.startsWith('flowchart') ||
            trimmedCode.includes('graph TD') ||
            trimmedCode.includes('graph LR')) {
            // Return code without line numbers for Mermaid diagrams
            return highlightedCode;
        }
        
        // Split into lines and create simple structure
        const lines = highlightedCode.split('\n');
        const numberedLines = lines.map((line, index) => {
            const lineNumber = index + 1;
            const content = line || ' '; // Handle empty lines
            return `<div class="prism-line"><span class="prism-line-number">${lineNumber}</span><span class="prism-content">${content}</span></div>`;
        });
        
        return numberedLines.join('');
    }
    
    async loadKatas() {
        try {
            const response = await fetch('katas.json');
            if (!response.ok) {
                throw new Error(`Failed to load katas: ${response.status}`);
            }
            const data = await response.json();
            this.katas = data.katas;
            this.metadata = data.metadata;
            console.log('Loaded katas:', this.katas);
            console.log('Kata metadata:', this.metadata);
            
            // Update page title if metadata is available
            if (this.metadata && this.metadata.title) {
                document.title = `${this.metadata.title} - Coding Dojo`;
            }
        } catch (error) {
            console.error('Error loading katas:', error);
            // Fallback to empty array with error message
            this.katas = [];
            this.showKataLoadError(error);
        }
    }
    
    showKataLoadError(error) {
        const kataList = document.getElementById('kataList');
        kataList.innerHTML = `
            <li class="error-message">
                <div style="color: #ff6b6b; padding: 20px; text-align: center;">
                    <h4>Error Loading Katas</h4>
                    <p>${error.message}</p>
                    <p style="font-size: 0.9em;">Please check your network connection and try refreshing the page.</p>
                </div>
            </li>
        `;
    }
    
    renderKataList() {
        const kataList = document.getElementById('kataList');
        kataList.innerHTML = '';
        
        this.katas.forEach((kata, index) => {
            const li = document.createElement('li');
            li.className = 'kata-item';
            li.dataset.index = index;
            li.innerHTML = `
                <div class="kata-title">${kata.title}</div>
                <div class="kata-meta">${kata.level} • ${kata.duration}</div>
            `;
            kataList.appendChild(li);
        });
    }
    
    setupEventListeners() {
        document.getElementById('kataList').addEventListener('click', (e) => {
            const kataItem = e.target.closest('.kata-item');
            if (kataItem) {
                const index = parseInt(kataItem.dataset.index);
                this.loadKata(index);
                
                // Update active state
                document.querySelectorAll('.kata-item').forEach(item => {
                    item.classList.remove('active');
                });
                kataItem.classList.add('active');
            }
        });
    }
    
    async loadKata(index) {
        const kata = this.katas[index];
        const viewer = document.getElementById('kataViewer');
        
        viewer.innerHTML = '<div class="loading">Loading kata...</div>';
        
        try {
            const response = await fetch(`katas/${kata.file}`);
            if (!response.ok) {
                throw new Error(`Failed to load kata: ${response.status}`);
            }
            
            const markdown = await response.text();
            const html = marked.parse(markdown);
            
            viewer.innerHTML = `<div class="kata-content">${html}</div>`;
            this.currentViewer = viewer;
            
            // Apply Prism syntax highlighting and line numbers after markdown processing
            this.applyCustomHighlighting(viewer);
            
            // Render Mermaid diagrams if available
            if (typeof mermaid !== 'undefined') {
                this.renderMermaidDiagrams(viewer);
            }
            
            // Apply dialogue styling
            this.applyDialogueStyling(viewer);
            
        } catch (error) {
            viewer.innerHTML = `
                <div class="loading" style="color: #ff6b6b;">
                    <h3>Error Loading Kata</h3>
                    <p>${error.message}</p>
                    <p>Make sure the kata file exists in the content/katas/ directory.</p>
                </div>
            `;
        }
    }
    
    renderMermaidDiagrams(viewer) {
        const mermaidBlocks = viewer.querySelectorAll('pre code.language-mermaid, pre code[class*="mermaid"]');
        
        mermaidBlocks.forEach((block, index) => {
            try {
                const mermaidCode = block.textContent;
                const mermaidId = `mermaid-diagram-${Date.now()}-${index}`;
                
                // Create a div to hold the rendered diagram
                const diagramDiv = document.createElement('div');
                diagramDiv.className = 'mermaid-diagram';
                diagramDiv.id = mermaidId;
                diagramDiv.style.textAlign = 'center';
                diagramDiv.style.margin = '20px 0';
                diagramDiv.style.padding = '20px';
                diagramDiv.style.backgroundColor = '#1a1a1a';
                diagramDiv.style.borderRadius = '8px';
                diagramDiv.style.border = '1px solid #333';
                
                // Render the diagram
                mermaid.render(mermaidId + '-svg', mermaidCode)
                    .then(({svg, bindFunctions}) => {
                        diagramDiv.innerHTML = svg;
                        if (bindFunctions) {
                            bindFunctions(diagramDiv);
                        }
                    })
                    .catch(err => {
                        console.error('Mermaid rendering error:', err);
                        diagramDiv.innerHTML = `<div style="color: #ff6b6b; padding: 10px;">Error rendering diagram: ${err.message}</div>`;
                    });
                
                // Replace the code block with the diagram
                block.parentElement.parentElement.replaceChild(diagramDiv, block.parentElement);
                
            } catch (err) {
                console.error('Failed to process Mermaid diagram:', err);
            }
        });
    }
    
    applyDialogueStyling(viewer) {
        // Find all paragraphs in the kata content
        const paragraphs = viewer.querySelectorAll('.kata-content p');
        
        paragraphs.forEach(p => {
            const text = p.textContent.trim();
            const innerHTML = p.innerHTML;
            
            // Check for Sensei dialogue
            if (text.startsWith('Sensei:') || innerHTML.includes('<strong>Sensei:</strong>')) {
                p.classList.add('dialogue-sensei');
                this.addTTSButton(p, 'sensei');
            }
            // Check for Deshi dialogue
            else if (text.startsWith('Deshi:') || innerHTML.includes('<strong>Deshi:</strong>')) {
                p.classList.add('dialogue-deshi');
                this.addTTSButton(p, 'deshi');
            }
            // Check for narrative text (text in italics or starting with *)
            else if (p.innerHTML.startsWith('<em>') || text.startsWith('*')) {
                p.classList.add('narrative');
            }
        });
    }

    addTTSButton(paragraph, speaker) {
        // Create TTS play button
        const playButton = document.createElement('button');
        playButton.className = 'tts-play-button';
        playButton.setAttribute('aria-label', `Play ${speaker} dialogue`);
        playButton.title = `Play ${speaker} dialogue`;
        
        // Add click event for TTS
        playButton.addEventListener('click', () => {
            this.speakText(paragraph, playButton, speaker);
        });
        
        // Append button to the paragraph
        paragraph.appendChild(playButton);
    }

    speakText(paragraph, button, speaker) {
        // Check if native Speech Synthesis is available
        if (!window.speechSynthesis) {
            console.warn('Speech Synthesis not supported in this browser');
            return;
        }

        // Ensure voices are loaded
        this.ensureVoicesLoaded(() => {
            this.performSpeech(paragraph, button, speaker);
        });
    }

    ensureVoicesLoaded(callback) {
        const voices = window.speechSynthesis.getVoices();
        if (voices.length > 0) {
            callback();
        } else {
            // Voices not loaded yet, wait for the voiceschanged event
            const voicesChangedHandler = () => {
                window.speechSynthesis.removeEventListener('voiceschanged', voicesChangedHandler);
                callback();
            };
            window.speechSynthesis.addEventListener('voiceschanged', voicesChangedHandler);
            
            // Fallback timeout in case the event doesn't fire
            setTimeout(() => {
                window.speechSynthesis.removeEventListener('voiceschanged', voicesChangedHandler);
                callback();
            }, 1000);
        }
    }

    performSpeech(paragraph, button, speaker) {
        // Stop any current speech
        if (window.speechSynthesis.speaking) {
            window.speechSynthesis.cancel();
            // Reset all buttons
            document.querySelectorAll('.tts-play-button').forEach(btn => {
                btn.classList.remove('speaking');
            });
            
            // If clicking the same button that was speaking, just stop
            if (button.classList.contains('speaking')) {
                return;
            }
        }

        // Get the text content, removing the speaker label
        let textToSpeak = paragraph.textContent.trim();
        textToSpeak = textToSpeak.replace(/^(Sensei:|Deshi:)\s*/, '');
        
        if (!textToSpeak) return;

        const utterance = new SpeechSynthesisUtterance(textToSpeak);
        
        // Force English language
        utterance.lang = 'en-US';
        
        // Find appropriate voice based on speaker with safe guards & retry
        const maxVoiceRetry = 5;
        if (!this._voiceRetryCounts) this._voiceRetryCounts = new WeakMap();

        const attempt = this._voiceRetryCounts.get(button) || 0;
        const rawVoices = (window.speechSynthesis.getVoices() || []).filter(v => v && v.name && v.lang);
        console.log("Speaker:", speaker);
        console.log("Available voices:", rawVoices.map(v => `${v.name} (${v.lang})`));

        let selectedVoice;
        if (rawVoices.length === 0 && attempt < maxVoiceRetry) {
            console.warn(`No voices available (attempt ${attempt + 1}/${maxVoiceRetry}) - retrying shortly`);
            this._voiceRetryCounts.set(button, attempt + 1);
            setTimeout(() => this.performSpeech(paragraph, button, speaker), 300);
            return; // Defer until voices populate
        }

        if (rawVoices.length > 0) {
            if (speaker === 'sensei') {
                console.log("Selecting voice for Sensei");
                selectedVoice = rawVoices.find(v => v.lang.includes('en-GB') && v.name.toLowerCase().includes('female'))
                    || rawVoices.find(v => v.lang.includes('en-GB'))
                    || rawVoices.find(v => v.name.toLowerCase().includes('female') && v.lang.startsWith('en'))
                    || rawVoices.find(v => v.lang && v.lang.startsWith('en'));
            } else {
                console.log("Selecting voice for Deshi");
                selectedVoice = rawVoices.find(v => v.lang.includes('en-US') && v.name.toLowerCase().includes('male'))
                    || rawVoices.find(v => v.lang.includes('en-US'))
                    || rawVoices.find(v => v.name.toLowerCase().includes('male') && v.lang.startsWith('en'))
                    || rawVoices.find(v => v.lang && v.lang.startsWith('en'));
            }
            if (selectedVoice) {
                try {
                    utterance.voice = selectedVoice;
                    console.log(`Using ${speaker} voice: ${selectedVoice.name} (${selectedVoice.lang})`);
                } catch (e) {
                    console.warn('Failed to set selected voice, proceeding with default:', e);
                }
            } else {
                console.warn(`No suitable voice match for ${speaker}, using browser default`);
            }
        } else {
            console.warn('Proceeding without explicit voice (browser default) after retries');
        }
        
        // Apply your specified voice presets
        utterance.rate = speaker === 'sensei' ? 0.7 : 1.1; // Elderly slow vs youthful fast
        utterance.pitch = speaker === 'sensei' ? 0.7 : 1.2; // Deep aged vs high young
        utterance.volume = speaker === 'sensei' ? 0.95 : 1; // Softer elderly vs full young

        utterance.onstart = () => {
            button.classList.add('speaking');
            console.log(`${speaker} speaking: "${textToSpeak.substring(0, 50)}..."`);
        };
        
        utterance.onend = () => {
            button.classList.remove('speaking');
            console.log(`${speaker} finished speaking`);
        };
        
        utterance.onerror = () => {
            button.classList.remove('speaking');
            console.warn(`TTS error for ${speaker}`);
        };

        window.speechSynthesis.speak(utterance);
    }
}

// Export for use in other modules if needed
if (typeof module !== 'undefined' && module.exports) {
    module.exports = GitKataViewer;
}
