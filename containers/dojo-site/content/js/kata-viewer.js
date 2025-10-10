/**
 * GitKataViewer - Java Abstraction Kata Viewer
 * Handles loading, displaying, and enhancing kata content with syntax highlighting,
 * Mermaid diagrams, dialogue styling, and text-to-speech functionality.
 */
class GitKataViewer {
    constructor() {
        this.katas = [];
        this.currentViewer = null;
        this.knowledgeCheck = new KnowledgeCheck();
        
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
        let lines = highlightedCode.split('\n');
        
        // Remove trailing empty lines that come from markdown formatting
        while (lines.length > 0 && lines[lines.length - 1].trim() === '') {
            lines.pop();
        }
        
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
            
            // Load front matter from markdown files
            await this.loadKataFrontMatter();
            
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
    
    async loadKataFrontMatter() {
        try {
            for (let kata of this.katas) {
                try {
                    const response = await fetch(`katas/${kata.file}`);
                    if (response.ok) {
                        const markdownContent = await response.text();
                        const frontMatter = this.parseFrontMatter(markdownContent);
                        // Merge front matter with existing kata data
                        Object.assign(kata, frontMatter);
                    }
                } catch (error) {
                    console.warn(`Failed to load front matter for ${kata.file}:`, error);
                }
            }
        } catch (error) {
            console.warn('Error loading kata front matter:', error);
        }
    }
    
    parseFrontMatter(content) {
        const frontMatterRegex = /^---\s*\n([\s\S]*?)\n---\s*\n/;
        const match = content.match(frontMatterRegex);
        
        if (!match) return {};
        
        const yamlContent = match[1];
        const frontMatter = {};
        
        // Simple YAML parser for basic key-value pairs and arrays
        const lines = yamlContent.split('\n');
        let currentKey = null;
        let currentArray = null;
        
        for (const line of lines) {
            const trimmed = line.trim();
            if (!trimmed || trimmed.startsWith('#')) continue;
            
            if (trimmed.startsWith('- ')) {
                // Array item
                if (currentArray) {
                    currentArray.push(trimmed.substring(2).replace(/['"]/g, ''));
                }
            } else if (trimmed.includes(':')) {
                // Key-value pair
                const [key, ...valueParts] = trimmed.split(':');
                const value = valueParts.join(':').trim();
                
                if (value.startsWith('"') && value.endsWith('"')) {
                    // String value
                    frontMatter[key.trim()] = value.slice(1, -1);
                } else if (value === '') {
                    // Array follows
                    currentKey = key.trim();
                    currentArray = [];
                    frontMatter[currentKey] = currentArray;
                } else if (!isNaN(value)) {
                    // Number value
                    frontMatter[key.trim()] = parseInt(value);
                } else {
                    // Plain string
                    frontMatter[key.trim()] = value.replace(/['"]/g, '');
                }
            }
        }
        
        return frontMatter;
    }
    
    stripFrontMatter(content) {
        const frontMatterRegex = /^---\s*\n[\s\S]*?\n---\s*\n/;
        return content.replace(frontMatterRegex, '');
    }
    
    renderFrontMatterHeader(frontMatter, kata) {
        // Merge front matter with kata data, prioritizing front matter
        const data = { ...kata, ...frontMatter };
        
        // Format concepts list
        const conceptsList = data.concepts && data.concepts.length > 0
            ? data.concepts.map(concept => `<span class="fm-concept-tag">${concept}</span>`).join('')
            : '';
        
        // Format prerequisites list
        const prerequisitesList = data.prerequisites && data.prerequisites.length > 0
            ? data.prerequisites.map(prereq => `<li>${prereq}</li>`).join('')
            : '<li>None</li>';
        
        // Format duration
        const duration = data.duration || (data.estimated_time ? `${data.estimated_time} minutes` : 'Unknown duration');
        
        // Get level information
        const level = (data.level || data.difficulty || 'beginner').toLowerCase();
        const levelCapitalized = level.charAt(0).toUpperCase() + level.slice(1);
        const ninjaIcon = this.getNinjaIcon(level);
        
        return `
            <div class="front-matter-header">
                <div class="fm-title-section">
                    <h1 class="fm-title">${data.title || 'Untitled Kata'}</h1>
                    <div class="fm-level-badge level-${level}">
                        <span class="fm-level-text">${levelCapitalized} Level</span>
                    </div>
                </div>
                
                ${data.goal ? `
                    <div class="fm-goal-section">
                        <div class="fm-goal-card">
                            <div class="fm-characters-dojo">
                                <div class="fm-ninja-container">
                                    <img src="images/${ninjaIcon}" alt="${levelCapitalized} Level Ninja" class="fm-character-icon fm-ninja-icon" title="${levelCapitalized} Level" />                                    
                                </div>
                                <div class="fm-sensei-container">
                                    <img src="images/sensei.png" alt="Sensei" class="fm-character-icon fm-sensei-icon" />                                    
                                </div>
                            </div>
                            <div class="fm-meta-label">Goal</div>
                            <div class="fm-meta-value">${data.goal}</div>
                        </div>
                    </div>
                ` : ''}
                
                <div class="fm-meta-grid">
                    ${data.subject ? `
                        <div class="fm-meta-item">
                            <div class="fm-meta-label">Subject</div>
                            <div class="fm-meta-value">${data.subject}</div>
                        </div>
                    ` : ''}
                    
                    ${!data.subject && !data.goal ? `
                        <div class="fm-meta-item">
                            <div class="fm-meta-label">Description</div>
                            <div class="fm-meta-value">No description available</div>
                        </div>
                    ` : ''}
                    
                    <div class="fm-meta-item">
                        <div class="fm-meta-label">Category</div>
                        <div class="fm-meta-value fm-category-${(data.category || 'unknown').toLowerCase()}">${data.category || 'Unknown'}</div>
                    </div>
                    
                    <div class="fm-meta-item">
                        <div class="fm-meta-label">Duration</div>
                        <div class="fm-meta-value">${duration}</div>
                    </div>
                </div>
                
                ${conceptsList ? `
                    <div class="fm-concepts-section">
                        <div class="fm-section-title">Key Concepts</div>
                        <div class="fm-concepts-list">${conceptsList}</div>
                    </div>
                ` : ''}
                
                ${data.prerequisites && data.prerequisites.length > 0 ? `
                    <div class="fm-prerequisites-section">
                        <div class="fm-section-title">Prerequisites</div>
                        <ul class="fm-prerequisites-list">${prerequisitesList}</ul>
                    </div>
                ` : ''}
            </div>
        `;
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
        
        // Group katas by category
        const groupedKatas = this.katas.reduce((groups, kata, index) => {
            const category = kata.category;
            if (!groups[category]) {
                groups[category] = [];
            }
            groups[category].push({...kata, originalIndex: index});
            return groups;
        }, {});
        
        // Render each category group
        Object.keys(groupedKatas).forEach(category => {
            // Add category header
            const categoryHeader = document.createElement('li');
            categoryHeader.className = 'category-header';
            categoryHeader.innerHTML = `
                <div class="category-title">${category} Katas</div>
            `;
            kataList.appendChild(categoryHeader);
            
            // Add katas in this category
            groupedKatas[category].forEach(kata => {
                const li = document.createElement('li');
                li.className = `kata-item kata-${kata.category.toLowerCase()} kata-level-${(kata.level || kata.difficulty || 'beginner').toLowerCase()}`;
                li.dataset.index = kata.originalIndex;
                
                // Format duration
                const duration = kata.duration || kata.estimated_time ? 
                    (kata.duration || `${kata.estimated_time} min`) : 
                    'Unknown duration';
                
                const level = (kata.level || kata.difficulty || 'beginner').toLowerCase();
                const levelCapitalized = level.charAt(0).toUpperCase() + level.slice(1);
                
                li.innerHTML = `
                    <div class="kata-content">
                        <div class="kata-title">${kata.title || 'Untitled Kata'}</div>
                        <div class="kata-subject">${kata.subject || kata.goal || 'No description available'}</div>
                        <div class="kata-meta">
                            <span class="kata-level level-${level}">${levelCapitalized}</span>
                            <span class="duration">${duration}</span>
                        </div>
                    </div>
                `;
                kataList.appendChild(li);
            });
        });
    }
    
    getNinjaIcon(level) {
        switch (level.toLowerCase()) {
            case 'beginner':
                return 'ninja-white.png';
            case 'intermediate':
                return 'ninja-orange.png';
            case 'advanced':
                return 'ninja-red.png';
            default:
                return 'ninja-white.png';
        }
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
            // Parse front matter and strip it from content
            const frontMatter = this.parseFrontMatter(markdown);
            let contentWithoutFrontMatter = this.stripFrontMatter(markdown);
            
            // Process knowledge check sections before converting to HTML
            contentWithoutFrontMatter = this.knowledgeCheck.processKnowledgeCheck(contentWithoutFrontMatter);
            
            const html = marked.parse(contentWithoutFrontMatter);
            
            // Create front matter header
            const frontMatterHtml = this.renderFrontMatterHeader(frontMatter, kata);
            
            viewer.innerHTML = `
                ${frontMatterHtml}
                <div class="kata-content">${html}</div>
            `;
            this.currentViewer = viewer;
            
            // Apply Prism syntax highlighting and line numbers after markdown processing
            this.applyCustomHighlighting(viewer);
            
            // Render Mermaid diagrams if available
            if (typeof mermaid !== 'undefined') {
                this.renderMermaidDiagrams(viewer);
            }
            
            // Apply dialogue styling
            this.applyDialogueStyling(viewer);
            
            // Initialize knowledge check interactions
            this.knowledgeCheck.initializeEventListeners(viewer);
            
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
            let innerHTML = p.innerHTML;
            
            // Check for Sensei dialogue
            if (text.startsWith('Sensei:') || innerHTML.includes('<strong>Sensei:</strong>')) {
                // Remove the "Sensei:" label from the content
                innerHTML = innerHTML.replace(/^<strong>Sensei:<\/strong>\s*/, '').replace(/^Sensei:\s*/, '');
                
                // Create new dialogue structure
                const dialogueHtml = `
                    <div class="dialogue-avatar">
                        <img src="images/sensei.png" alt="Sensei" />
                    </div>
                    <div class="dialogue-content">${innerHTML}</div>
                `;
                
                p.classList.add('dialogue-sensei');
                p.innerHTML = dialogueHtml;
                this.addTTSButton(p, 'sensei');
            }
            // Check for Deshi dialogue
            else if (text.startsWith('Deshi:') || innerHTML.includes('<strong>Deshi:</strong>')) {
                // Remove the "Deshi:" label from the content
                innerHTML = innerHTML.replace(/^<strong>Deshi:<\/strong>\s*/, '').replace(/^Deshi:\s*/, '');
                
                // Create new dialogue structure
                const dialogueHtml = `
                    <div class="dialogue-avatar">
                        <img src="images/deshi.png" alt="Deshi" />
                    </div>
                    <div class="dialogue-content">${innerHTML}</div>
                `;
                
                p.classList.add('dialogue-deshi');
                p.innerHTML = dialogueHtml;
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
        
        // Find the dialogue-avatar container and append button to it
        const avatarContainer = paragraph.querySelector('.dialogue-avatar');
        if (avatarContainer) {
            avatarContainer.appendChild(playButton);
        }
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
