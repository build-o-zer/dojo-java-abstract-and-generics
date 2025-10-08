/**
 * Kata Viewer Initialization Script
 * Handles dependency loading, fallbacks, and application startup
 */

// Wait for all dependencies to load with retry logic
let initRetryCount = 0;
const maxInitRetries = 20; // Wait up to 2 seconds for libraries to load

async function initializeWhenReady() {
    console.log(`Checking dependencies (attempt ${initRetryCount + 1}/${maxInitRetries})...`);
    console.log('Marked available:', typeof marked !== 'undefined');
    console.log('Hljs available:', typeof hljs !== 'undefined');
    console.log('Mermaid available:', typeof mermaid !== 'undefined');
    console.log('Speech Synthesis available:', 'speechSynthesis' in window);
    
    // Check if required libraries are loaded
    if (typeof marked === 'undefined') {
        initRetryCount++;
        if (initRetryCount < maxInitRetries) {
            console.log('Marked.js library not loaded - retrying...');
            setTimeout(initializeWhenReady, 100); // Retry after 100ms
            return;
        } else {
            console.error('Marked.js failed to load after all retries - cannot continue');
            document.body.innerHTML = '<div style="color: red; padding: 20px; font-family: Arial;">Error: Required markdown library failed to load. Please check your internet connection and refresh the page.</div>';
            return;
        }
    }
    
    if (typeof hljs === 'undefined') {
        console.warn('Highlight.js library not loaded - syntax highlighting will be disabled');
        // Continue without syntax highlighting
    }
    
    if (typeof mermaid === 'undefined') {
        console.warn('Mermaid library not loaded - diagram rendering will be disabled');
        // Continue without diagrams
    }
    
    if (!('speechSynthesis' in window)) {
        console.warn('Speech Synthesis not supported - TTS will be disabled');
    } else {
        console.log('Native Speech Synthesis available - TTS enabled');
    }
    
    console.log('Initializing GitKataViewer...');
    const viewer = new GitKataViewer();
    await viewer.initialize();
    window.kataViewer = viewer; // Make it globally accessible if needed
}

// Initialize the kata viewer when page loads, with delay to allow fallback scripts to load
document.addEventListener('DOMContentLoaded', () => {
    setTimeout(initializeWhenReady, 500); // Give more time for fallback scripts
});
