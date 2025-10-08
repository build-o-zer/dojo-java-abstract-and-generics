# Enhanced Dialogue Styling Guide

## 🎨 Visual Improvements to Kata Dialogue

The kata viewer now features enhanced styling to make sensei-deshi conversations more visually engaging and easier to follow.

## 🎭 Character Styling

### Sensei (Master) 🧙‍♂️
- **Position**: Left-aligned
- **Color Theme**: Warm golden tones
- **Background**: Golden gradient with subtle transparency
- **Border**: Left border in gold (#ffd700)
- **Icon**: 🧙‍♂️ (Wizard emoji representing wisdom)
- **Text Color**: Gold highlights for the name

### Deshi (Student) 🥋
- **Position**: Right-aligned
- **Color Theme**: Cool blue tones
- **Background**: Blue gradient with subtle transparency
- **Border**: Right border in steel blue (#6495ed)
- **Icon**: 🥋 (Martial arts uniform representing the student)
- **Text Color**: Light blue highlights for the name

### Narrative Text ✨
- **Position**: Center-aligned
- **Color Theme**: Purple mystical tones
- **Style**: Italicized text
- **Icon**: ✨ (Sparkles representing scene setting)
- **Usage**: For scene descriptions and narrative elements

## 📱 Responsive Design

The dialogue styling adapts to different screen sizes:
- **Desktop**: Full 85% width with proper alignment
- **Mobile**: 95% width with adjusted padding and smaller icons

## 🔧 Technical Implementation

### Automatic Detection
The system automatically detects dialogue patterns:
```javascript
// Sensei dialogue detection
if (text.startsWith('Sensei:') || innerHTML.includes('<strong>Sensei:</strong>')) {
    p.classList.add('dialogue-sensei');
}

// Deshi dialogue detection
else if (text.startsWith('Deshi:') || innerHTML.includes('<strong>Deshi:</strong>')) {
    p.classList.add('dialogue-deshi');
}

// Narrative text detection
else if (p.innerHTML.startsWith('<em>') || text.startsWith('*')) {
    p.classList.add('narrative');
}
```

### CSS Classes Applied
- `.dialogue-sensei` - For sensei (teacher) dialogue
- `.dialogue-deshi` - For deshi (student) dialogue  
- `.narrative` - For narrative/scene setting text

## 🎨 Visual Examples

When rendered, the dialogue will appear as:

```
🧙‍♂️ **Sensei:** "Welcome to the dojo, young deshi..."
                                    [Left-aligned with golden styling]

                    **Deshi:** "Thank you, sensei! I'm ready to learn." 🥋
                                    [Right-aligned with blue styling]

            ✨ *The sensei nods approvingly as wind rustles through the bamboo* ✨
                                    [Center-aligned narrative]
```

## 🌟 Benefits

1. **Visual Clarity**: Easy to distinguish between speakers
2. **Cultural Authenticity**: Colors and icons reflect dojo aesthetics
3. **Improved Flow**: Left-right alignment creates natural conversation flow
4. **Accessibility**: High contrast and clear visual hierarchy
5. **Responsive**: Works well on all device sizes

## 🚀 Usage in Katas

When writing new katas, use these formats for automatic styling:

```markdown
**Sensei:** "Your dialogue here..."

**Deshi:** "Student response here..."

*Narrative description in italics*
```

The system will automatically apply the appropriate styling, icons, and positioning to create an immersive learning experience!

---

*The way of visual harmony enhances the way of learning.* - Dojo Design Principle
