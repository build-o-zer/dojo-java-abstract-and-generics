# Git Kata Template

Use this template to create new Git katas for the dojo.

## File Structure

```
kata-name.md                 # Main kata content
```

## Markdown Template

```markdown
# Git Kata: [Kata Name]

**Subject:** [Main Git concept/command]  
**Goal:** [What the student will learn]  
**Level:** [Beginner/Intermediate/Advanced]  
**Duration:** [Estimated completion time]  

---

## 道場での対話 (Dojo Dialogue)

### Scene: [Setting description]

*[Narrative setup describing the situation]*

**Sensei:** "[Opening question or observation]"

**Deshi:** "[Student's problem or confusion]"

**Sensei:** "[Wisdom and introduction to the solution]"

---

## The Teaching

**Sensei:** "[Explanation of the core concepts]"

```bash
# Example commands
git command --option
```

**Deshi:** "[Follow-up questions]"

**Sensei:** "[Deeper explanations with examples]"

---

## The Practice (実践)

**Sensei:** "[Step-by-step instructions]"

### Basic Usage
```bash
git basic-command
```

### Advanced Techniques
```bash
git advanced-command --with-options
```

---

## Common Pitfalls (落とし穴)

**Sensei warns:** "[Common mistakes to avoid]"

1. **[Pitfall 1]**
   ```bash
   # Wrong way
   git wrong-command
   
   # Right way  
   git correct-command
   ```

---

## The Challenge (挑戦)

**Sensei:** "[Practice scenario setup]"

### Setup
```bash
# Commands to create practice environment
```

### Your Mission
1. [Task 1]
2. [Task 2]
3. [Task 3]

### Expected Result
```
[What the final state should look like]
```

---

## Reflection (反省)

**Sensei:** "[Closing wisdom question]"

**Deshi:** "[Student's learning summary]"

**Sensei:** "[Final wisdom and encouragement]"

### Key Takeaways
- [Learning point 1]
- [Learning point 2]
- [Learning point 3]

---

## Next Steps

Continue your journey with:
- [Related Kata 1](./related-kata-1.md)
- [Related Kata 2](./related-kata-2.md)

---

*"[Relevant dojo wisdom quote]"* - Dojo Proverb
```

## Adding to the Viewer

To add your new kata to the viewer, update the `katas` array in `kata-viewer.html`:

```javascript
{
    title: "Your Kata Title",
    file: "your-kata-file.md", 
    subject: "Git Subject",
    level: "Intermediate",
    duration: "20 min"
}
```

## Style Guidelines

### Dialogue Format
- **Sensei:** Use for teaching moments and wisdom
- **Deshi:** Use for questions and confusion
- *Italics* for narrative and scene setting

### Code Examples
- Use fenced code blocks with language specification
- Include both correct and incorrect examples
- Show expected output when helpful

### Japanese Elements
- Include relevant Japanese terms with translations
- Use traditional concepts (道, 練習, 実践, 反省, etc.)
- Maintain respectful tone throughout

### Structure
- Keep sections balanced in length
- Include practical hands-on elements
- End with actionable takeaways
- Link to related concepts

## Content Guidelines

- **Practical**: Focus on real-world scenarios
- **Progressive**: Build complexity gradually  
- **Interactive**: Include practice exercises
- **Memorable**: Use storytelling and metaphors
- **Comprehensive**: Cover common pitfalls and edge cases
