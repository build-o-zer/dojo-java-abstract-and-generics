# Git Kata System Documentation

## Overview

The Git Kata system is a comprehensive learning platform for intermediate Git commands, built using a traditional Japanese dojo approach with sensei-deshi dialogues. The system combines markdown-based content with a JavaScript-powered web viewer for an immersive learning experience.

## File Structure Proposal

### 1. **Markdown-Based Kata Content**

```
katas/
├── README.md                           # Kata catalog and overview
├── TEMPLATE.md                         # Template for creating new katas
├── interactive-rebase-mastery.md       # Example kata
├── cherry-pick-mastery.md              # Example kata
└── [additional-kata-files].md          # Future katas
```

### 2. **Rendering Framework**

```
kata-viewer.html                        # Main kata viewer application
index.html                             # Landing page with link to katas
```

## Kata Structure Format

Each kata follows this standardized markdown structure:

```markdown
# Git Kata: [Title]

**Subject:** [Core concept]
**Goal:** [Learning objective]  
**Level:** [Beginner/Intermediate/Advanced]
**Duration:** [Estimated time]

---

## 道場での対話 (Dojo Dialogue)
[Sensei-Deshi conversation introducing the problem]

## The Teaching
[Core concepts and commands]

## The Practice (実践)  
[Hands-on examples and exercises]

## The Challenge (挑戦)
[Practice scenario for the student]

## Reflection (反省)
[Key takeaways and wisdom]

## Next Steps
[Links to related katas]
```

## Rendering Framework Details

### Technology Stack

- **Markdown Parser**: [Marked.js](https://marked.js.org/) v5.1.1
- **Syntax Highlighting**: [Highlight.js](https://highlightjs.org/) v11.9.0
- **Styling**: Custom CSS with Japanese dojo theme
- **Architecture**: Client-side SPA (Single Page Application)

### Key Features

1. **Sidebar Navigation**: Lists all available katas with metadata
2. **Dynamic Content Loading**: Fetches and renders markdown files on demand
3. **Syntax Highlighting**: Automatic code highlighting for bash/git commands
4. **Responsive Design**: Works on desktop and mobile devices
5. **Japanese Theming**: Traditional dojo aesthetics with appropriate colors and typography

### JavaScript Architecture

```javascript
class GitKataViewer {
    constructor() {
        this.katas = [
            {
                title: "Interactive Rebase Mastery",
                file: "interactive-rebase-mastery.md",
                subject: "Interactive Rebase",
                level: "Intermediate",
                duration: "20-30 min"
            }
            // ... more katas
        ];
    }
    
    // Core methods:
    // - renderKataList(): Builds sidebar navigation
    // - loadKata(index): Fetches and renders markdown content
    // - setupEventListeners(): Handles user interactions
}
```

## Content Guidelines

### Writing Style

1. **Sensei-Deshi Dialogue**:
   - **Sensei**: Provides wisdom, explanations, and guidance
   - **Deshi**: Asks questions, expresses confusion, shows learning
   - *Italics*: Used for narrative and scene setting

2. **Code Examples**:
   - Use fenced code blocks with `bash` language tag
   - Include both correct and incorrect examples
   - Show expected output when helpful

3. **Japanese Elements**:
   - Include relevant Japanese terms with meanings
   - Use traditional learning concepts (道, 練習, 実践, 反省)
   - Maintain respectful and authentic tone

### Content Structure

- **Progressive Learning**: Build complexity gradually
- **Practical Focus**: Real-world scenarios and problems
- **Interactive Elements**: Hands-on exercises and challenges
- **Memorable Format**: Storytelling and meaningful metaphors

## Technical Implementation

### Container Integration

The kata system is integrated into the dojo-site Docker container:

```dockerfile
# Copy the kata viewer and content
COPY containers/dojo-site/kata-viewer.html /usr/share/nginx/html/
COPY containers/dojo-site/katas/ /usr/share/nginx/html/katas/
```

### Accessibility

- **Main Landing Page**: http://localhost:9595/
- **Kata Viewer**: http://localhost:9595/kata-viewer.html
- **Individual Katas**: http://localhost:9595/katas/[kata-name].md

### Browser Support

- Modern browsers with ES6+ support
- JavaScript enabled (required for dynamic content loading)
- No server-side processing needed (static hosting friendly)

## Adding New Katas

### Step 1: Create Content

1. Use the template in `katas/TEMPLATE.md`
2. Follow the established format and style guidelines
3. Include practical examples and challenges

### Step 2: Update Catalog

Add the new kata to `katas/README.md` table.

### Step 3: Update Viewer

Add kata metadata to the `katas` array in `kata-viewer.html`:

```javascript
{
    title: "Your Kata Title",
    file: "your-kata-file.md", 
    subject: "Git Subject",
    level: "Intermediate",
    duration: "20 min"
}
```

### Step 4: Test

1. Rebuild the container: `docker compose build dojo-site`
2. Start the container: `docker compose up -d dojo-site`
3. Test at: http://localhost:9595/kata-viewer.html

## Educational Philosophy

The kata system follows traditional martial arts principles:

- **Practice (練習)**: Repetitive learning through guided exercises
- **Discipline (修行)**: Structured progression through difficulty levels
- **Reflection (反省)**: Understanding deeper principles behind techniques
- **Mastery (道)**: Gradual development of intuitive understanding

Each kata is designed to be:
- **Self-contained**: Can be completed independently
- **Progressive**: Builds on fundamental concepts
- **Practical**: Addresses real-world Git scenarios
- **Memorable**: Uses storytelling and metaphor for retention

## Benefits of This Approach

1. **Engagement**: Story format makes learning more interesting
2. **Context**: Problems are introduced naturally through dialogue
3. **Retention**: Metaphors and cultural elements aid memory
4. **Flexibility**: Markdown allows easy content creation and editing
5. **Accessibility**: Web-based viewer works across platforms
6. **Scalability**: Easy to add new katas and update existing ones

## Future Enhancements

Potential improvements to the system:

- **Progress Tracking**: Local storage to remember completed katas
- **Search Functionality**: Find katas by topic or command
- **Interactive Terminal**: Embedded terminal for hands-on practice
- **Community Features**: Comments or discussion sections
- **Assessment Tools**: Quizzes or automated checks
- **Advanced Topics**: More complex Git workflows and enterprise patterns

---

*"The way of Git is not in the commands themselves, but in understanding the flow of changes through time."* - Dojo Wisdom
