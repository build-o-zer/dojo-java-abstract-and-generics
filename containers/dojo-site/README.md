# Dojo Site Container

This container hosts a static welcome page for the Coding Dojo environment. It serves as the main landing page that introduces visitors to the concept of a coding dojo with a beautiful Japanese-themed design.

## Features

- **Japanese Dojo Theme**: Beautiful gradient background with traditional Japanese characters (道場, 礼, 忍, 学, 協) representing the dojo principles
- **Responsive Design**: Works well on both desktop and mobile devices
- **Educational Content**: Explains the coding dojo concept and principles
- **Visual Appeal**: Includes the main dojo image and elegant styling

## Container Details

- **Base Image**: nginx:alpine
- **Port**: 80 (mapped to host port 9595)
- **Static Files**:
  - `index.html` - Main welcome page
  - `dojo-image-git.png` - Main dojo image from resources

## Dojo Principles Featured

1. **Respect (礼 - Rei)**: Respect for code, colleagues, and the learning process
2. **Perseverance (忍 - Nin)**: Persistence in facing challenging problems
3. **Learning (学 - Gaku)**: Continuous learning and improvement
4. **Collaboration (協 - Kyō)**: Working together as a team

## Usage

The container is automatically started with the docker-compose stack and accessible at:

- <http://localhost:9595>

## Build Context

The container uses the root directory as build context to access the resources folder for the dojo image.
