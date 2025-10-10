/**
 * KnowledgeCheck - Interactive Knowledge Check System
 * Handles rendering of Pre-Dialog Knowledge Check questions with clickable answers
 * and displays checkmarks for correct responses.
 */
class KnowledgeCheck {
    constructor() {
        this.currentAnswers = new Map(); // question number -> selected answer
        this.correctAnswers = new Map(); // question number -> correct answer
    }

    /**
     * Processes markdown content to replace any Knowledge Check sections
     * with interactive HTML components
     */
    processKnowledgeCheck(markdown) {
        // Find all sections ending with "Knowledge Check"
        const knowledgeCheckRegex = /## ([^#\n]*Knowledge Check)\s*([\s\S]*?)(?=---|\n## |\n# |$)/g;
        let processedMarkdown = markdown;
        let match;
        let sectionCounter = 0;
        
        while ((match = knowledgeCheckRegex.exec(markdown)) !== null) {
            sectionCounter++;
            const sectionTitle = match[1];
            const knowledgeCheckContent = match[2];
            
            // Extract answers from the content (format: > Answers : 1:c;2:b;3:c)
            const answersRegex = /> Answers\s*:\s*(.+?)(?=\n|$)/;
            const answersMatch = knowledgeCheckContent.match(answersRegex);
            
            if (answersMatch) {
                this.parseCorrectAnswers(answersMatch[1], sectionCounter);
            }

            // Process each question
            const interactiveContent = this.convertToInteractiveQuestions(knowledgeCheckContent, sectionCounter);
            
            // Replace the original section with the interactive version
            const fullMatch = match[0];
            const replacement = `## ${sectionTitle}\n\n${interactiveContent}\n\n---`;
            processedMarkdown = processedMarkdown.replace(fullMatch, replacement);
        }
        
        return processedMarkdown;
    }

    /**
     * Parses the correct answers string (e.g., "1:c;2:b;3:c")
     */
    parseCorrectAnswers(answersString, sectionCounter = 1) {
        const answers = answersString.split(';');
        answers.forEach(answer => {
            const [questionNum, correctOption] = answer.split(':');
            if (questionNum && correctOption) {
                // Create unique key: section_question (e.g., "1_1", "1_2", "2_1", "2_2")
                const uniqueKey = `${sectionCounter}_${questionNum.trim()}`;
                this.correctAnswers.set(uniqueKey, correctOption.trim().toLowerCase());
            }
        });
    }

    /**
     * Converts markdown questions to interactive HTML
     */
    convertToInteractiveQuestions(content, sectionCounter = 1) {
        // Remove the answers line from content
        const contentWithoutAnswers = content.replace(/> Answers\s*:.*$/m, '').trim();
        
        // Parse questions using regex to capture question numbers
        const questionsData = this.parseQuestions(contentWithoutAnswers, sectionCounter);
        
        // Use a placeholder that will be replaced after markdown processing
        let result = '<div class="knowledge-check-placeholder" data-questions="' + 
                    btoa(JSON.stringify(questionsData)) + '" data-section="' + sectionCounter + '"></div>';
        
        return result;
    }

    /**
     * Parses questions from content using regex to properly extract question numbers
     */
    parseQuestions(content, sectionCounter = 1) {
        const questionsData = [];
        const questionRegex = /### Question (\d+):\s*([^\n]+)\n((?:(?!### Question \d+:)[\s\S])*)/g;
        let match;
        
        while ((match = questionRegex.exec(content)) !== null) {
            const questionNum = parseInt(match[1]);
            const questionText = match[2].trim();
            const fullContent = match[3].trim();
            
            // Separate additional content from options
            const { additionalContent, options } = this.parseQuestionContent(fullContent);
            
            questionsData.push({
                questionNum,
                questionText,
                additionalContent,
                options,
                sectionCounter,
                uniqueId: `${sectionCounter}_${questionNum}` // Unique ID for this question
            });
        }
        
        return questionsData;
    }

    /**
     * Parses question content to separate additional content from options
     */
    parseQuestionContent(content) {
        const lines = content.split('\n');
        const options = [];
        let additionalContentLines = [];
        let currentOption = null;
        let foundFirstOption = false;
        
        for (const line of lines) {
            const trimmedLine = line.trim();
            
            // Check if this is an option line (a), b), c), d))
            if (trimmedLine.match(/^[a-d]\)/)) {
                foundFirstOption = true;
                if (currentOption) {
                    options.push(currentOption);
                }
                currentOption = {
                    letter: trimmedLine.charAt(0),
                    text: trimmedLine.substring(2).trim()
                };
            } else if (currentOption && trimmedLine && 
                      !trimmedLine.startsWith('> Answers')) {
                // Continue previous option text
                currentOption.text += ' ' + trimmedLine;
            } else if (!foundFirstOption && trimmedLine && 
                      !trimmedLine.startsWith('> Answers')) {
                // This is additional content before the options
                additionalContentLines.push(line);
            }
        }
        
        if (currentOption) {
            options.push(currentOption);
        }
        
        const additionalContent = additionalContentLines.join('\n').trim();
        
        return { additionalContent, options };
    }

    /**
     * Renders a single question with interactive options
     */
    renderQuestion(questionData) {
        const { questionNum, questionText, additionalContent = '', options, uniqueId } = questionData;
        
        let html = `
        <div class="knowledge-question" data-question="${uniqueId}">
            <h4 class="question-title">Question ${questionNum}: ${this.processInlineCode(questionText)}</h4>
        `;

        // Add additional content if present (like code examples)
        if (additionalContent) {
            html += `<div class="question-additional-content">${this.renderAdditionalContent(additionalContent)}</div>`;
        }

        html += `<div class="question-options">`;

        options.forEach(option => {
            html += `
                <div class="question-option" data-question="${uniqueId}" data-answer="${option.letter}">
                    <span class="option-letter">${option.letter})</span>
                    <span class="option-text">${this.processInlineCode(option.text)}</span>
                    <span class="option-checkmark">✓</span>
                </div>
            `;
        });

        html += `
            </div>
        </div>
        `;

        return html;
    }

    /**
     * Renders additional content (like code blocks) within questions
     */
    renderAdditionalContent(content) {
        // Convert markdown code blocks to HTML with Prism highlighting
        let html = content;
        
        // Handle code blocks ```java ... ```
        html = html.replace(/```(\w+)?\n([\s\S]*?)```/g, (match, language, code) => {
            const lang = language || 'java';
            const trimmedCode = code.trim();
            
            // Use Prism for syntax highlighting if available
            if (typeof Prism !== 'undefined' && Prism.languages[lang]) {
                try {
                    const highlightedCode = Prism.highlight(trimmedCode, Prism.languages[lang], lang);
                    // Add line numbers similar to the main kata viewer
                    const numberedCode = this.addLineNumbersToHighlightedCode(highlightedCode);
                    return `<pre class="knowledge-code-block"><code class="language-${lang}">${numberedCode}</code></pre>`;
                } catch (err) {
                    console.warn(`Failed to highlight code with Prism for language '${lang}':`, err);
                    // Fallback to escaped HTML
                    return `<pre class="knowledge-code-block"><code class="language-${lang}">${this.escapeHtml(trimmedCode)}</code></pre>`;
                }
            } else {
                // Fallback when Prism is not available
                return `<pre class="knowledge-code-block"><code class="language-${lang}">${this.escapeHtml(trimmedCode)}</code></pre>`;
            }
        });
        
        // Handle other markdown formatting if needed
        html = html.replace(/\n/g, '<br>');
        
        return html;
    }

    /**
     * Adds line numbers to already highlighted code (similar to main kata viewer)
     */
    addLineNumbersToHighlightedCode(highlightedCode) {
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

    /**
     * Escapes HTML characters
     */
    escapeHtml(text) {
        const div = document.createElement('div');
        div.textContent = text;
        return div.innerHTML;
    }

    /**
     * Processes inline code snippets (backticks) in text and converts them to styled HTML
     */
    processInlineCode(text) {
        // Convert inline code `code` to <code class="inline-code">code</code>
        // Important: Escape HTML characters in the code content to prevent <T> from being interpreted as HTML tags
        return text.replace(/`([^`]+)`/g, (match, codeContent) => {
            const escapedCode = this.escapeHtml(codeContent);
            return `<code class="inline-code">${escapedCode}</code>`;
        });
    }

    /**
     * Processes placeholders in the rendered HTML and replaces them with interactive content
     */
    processPlaceholders(container) {
        const placeholders = container.querySelectorAll('.knowledge-check-placeholder');
        
        placeholders.forEach(placeholder => {
            const questionsData = JSON.parse(atob(placeholder.dataset.questions));
            const interactiveHTML = this.renderInteractiveQuestions(questionsData);
            
            // Replace the placeholder with the interactive content
            const tempDiv = document.createElement('div');
            tempDiv.innerHTML = interactiveHTML;
            const interactiveElement = tempDiv.firstElementChild;
            
            placeholder.parentNode.replaceChild(interactiveElement, placeholder);
        });
    }

    /**
     * Renders interactive questions from decoded data
     */
    renderInteractiveQuestions(questionsData) {
        let html = '<div class="knowledge-check-container">\n';
        
        questionsData.forEach(questionData => {
            html += this.renderQuestion(questionData);
        });
        
        html += '</div>';
        return html;
    }

    /**
     * Initializes event listeners for interactive elements
     */
    initializeEventListeners(container) {
        // First process any placeholders
        this.processPlaceholders(container);
        
        // Then add event listeners to the interactive elements
        const options = container.querySelectorAll('.question-option');
        
        options.forEach(option => {
            option.addEventListener('click', (event) => {
                this.handleOptionClick(event.target.closest('.question-option'));
            });
        });
    }

    /**
     * Handles clicking on an answer option
     */
    handleOptionClick(optionElement) {
        const uniqueQuestionId = optionElement.dataset.question; // Now uses unique ID like "1_1", "2_3"
        const selectedAnswer = optionElement.dataset.answer;
        const questionContainer = optionElement.closest('.knowledge-question');
        
        // Remove previous selections from this question
        questionContainer.querySelectorAll('.question-option').forEach(opt => {
            opt.classList.remove('selected', 'correct', 'incorrect');
        });
        
        // Mark this option as selected
        optionElement.classList.add('selected');
        
        // Store the answer using the unique ID
        this.currentAnswers.set(uniqueQuestionId, selectedAnswer);
        
        // Check if answer is correct
        const correctAnswer = this.correctAnswers.get(uniqueQuestionId);
        if (correctAnswer && selectedAnswer.toLowerCase() === correctAnswer) {
            optionElement.classList.add('correct');
            // Add a small delay for visual feedback
            setTimeout(() => {
                optionElement.querySelector('.option-checkmark').style.display = 'inline';
            }, 200);
        } else {
            optionElement.classList.add('incorrect');
        }
    }

    /**
     * Gets the current score (correct answers / total questions)
     */
    getScore() {
        let correct = 0;
        let total = this.correctAnswers.size;
        
        this.correctAnswers.forEach((correctAnswer, uniqueQuestionId) => {
            const userAnswer = this.currentAnswers.get(uniqueQuestionId);
            if (userAnswer && userAnswer.toLowerCase() === correctAnswer) {
                correct++;
            }
        });
        
        return { correct, total };
    }

    /**
     * Gets the score for a specific section
     */
    getSectionScore(sectionCounter) {
        let correct = 0;
        let total = 0;
        
        this.correctAnswers.forEach((correctAnswer, uniqueQuestionId) => {
            if (uniqueQuestionId.startsWith(`${sectionCounter}_`)) {
                total++;
                const userAnswer = this.currentAnswers.get(uniqueQuestionId);
                if (userAnswer && userAnswer.toLowerCase() === correctAnswer) {
                    correct++;
                }
            }
        });
        
        return { correct, total };
    }
}

// Make KnowledgeCheck available globally
window.KnowledgeCheck = KnowledgeCheck;
