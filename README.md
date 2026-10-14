# About the Repository
This repo contains both the 2D and 3D versions of my custom game engine. The 2D version is older, though likely more functional (theoretically), while the 3D version is newer, though mainly made to demo my ray-tracing.
To see both versions, stay in the `master` branch. The newest version of the ray-tracer is in the `Just-Raytracing-Full-Barebones` branch.

## 3d Ray Tracing Game Engine
### Summary
This is a project that originally started as a fork from my 2D game engine. As I was working on it, I began to focus more and more on utilizing ray tracing for the graphics.  
In the end, this isn't very functional as a game engine, but the ray tracing functionality still makes it one of my favorite projects I've ever worked on.  

I hope you'll take a look at some of the renders in the `renders/` directory!


### Technical Details
The ray tracing is done on the CPU in Java, meaning it's not very practical for real-time rendering. However, it does heavily utilize Java's multi-threading functionality, and I'm proud of how optimized I was able to get it given the limitations.
