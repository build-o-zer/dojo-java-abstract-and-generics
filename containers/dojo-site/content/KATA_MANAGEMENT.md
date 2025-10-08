# Kata Management

This document explains how to manage katas in the Coding Dojo system.

## Kata Configuration File

The katas are now externalized from the HTML and defined in `/containers/dojo-site/content/katas.json`.

### JSON Structure

```json
{
  "metadata": {
    "title": "Java Abstraction Katas",
    "description": "Progressive katas for learning Java abstraction concepts",
    "version": "1.0.0",
    "lastUpdated": "2025-10-07"
  },
  "katas": [
    {
      "id": "unique-kata-id",
      "title": "Kata Display Title",
      "file": "kata-filename.md",
      "subject": "Main Topic",
      "level": "Beginner|Intermediate|Advanced",
      "duration": "XX-YY min",
      "tags": ["tag1", "tag2", "tag3"],
      "prerequisites": ["Prerequisite 1", "Prerequisite 2"]
    }
  ]
}
```

### Fields Explanation

- **metadata**: Contains information about the kata collection
  - `title`: Collection title (used for page title)
  - `description`: Brief description of the collection
  - `version`: Version number for tracking changes
  - `lastUpdated`: Date of last update

- **katas**: Array of individual kata definitions
  - `id`: Unique identifier for the kata (for future reference)
  - `title`: Display name shown in the kata list
  - `file`: Markdown filename in the `katas/` directory
  - `subject`: Brief subject description
  - `level`: Difficulty level (affects styling and filtering)
  - `duration`: Estimated completion time
  - `tags`: Array of tags for categorization (future feature)
  - `prerequisites`: Array of prerequisite knowledge (future feature)

## Adding New Katas

1. **Create the markdown file**: Add your kata content in `/containers/dojo-site/content/katas/your-kata-name.md`
2. **Update katas.json**: Add a new entry to the `katas` array
3. **Rebuild the Docker container**: Run `docker compose down && docker compose up --build`

### Example New Kata Entry

```json
{
  "id": "java-interfaces-basics",
  "title": "Java Interfaces Fundamentals",
  "file": "java-interfaces-basics.md",
  "subject": "Interface Design",
  "level": "Intermediate",
  "duration": "20-25 min",
  "tags": ["interfaces", "contracts", "polymorphism"],
  "prerequisites": ["Understanding of abstract classes", "Basic inheritance knowledge"]
}
```

## Updating Existing Katas

1. **Update the markdown**: Modify the content in `/containers/dojo-site/content/katas/filename.md`
2. **Update metadata if needed**: Change duration, level, or other metadata in `katas.json`
3. **Update version**: Increment the version number and update `lastUpdated` in metadata
4. **Rebuild the container**: Apply changes with Docker rebuild

## Benefits of External Configuration

- ✅ **Easy Management**: Update katas without modifying HTML/JavaScript code
- ✅ **Version Control**: Track changes to kata definitions separately
- ✅ **Scalability**: Easy to add/remove katas as the collection grows
- ✅ **Flexibility**: Support for future features like filtering, search, etc.
- ✅ **Maintainability**: Clean separation between content and presentation logic
- ✅ **API-like**: Can be consumed by other tools or systems

## Future Enhancements

The external JSON structure supports future features like:
- Kata filtering by level, tags, or subject
- Search functionality
- Prerequisites tracking and validation
- Progress tracking per user
- Dynamic loading of kata collections
- API endpoints for kata management

## Error Handling

The kata viewer includes robust error handling:
- Network failures show user-friendly error messages
- Invalid JSON structure is caught and reported
- Missing kata files are handled gracefully
- Fallback content ensures the page remains functional
