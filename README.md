# jam

A [libGDX](https://libgdx.com/) project generated with [gdx-liftoff](https://github.com/tommyettinger/gdx-liftoff).

This project was generated with a template including simple application launchers and an `ApplicationAdapter` extension that draws libGDX logo.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3.

## Gradle

This project uses [Gradle](http://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/lib`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.


## Animations

Animations for an agents need to be prepared in a certain way.
ALL animations asscoiated with a single agent needs to be individual png images that are the same size and same name. 
The only difference is the index numbers on the end of the file name.
If you wish to layer animations on top of each other, you need to make sure the index numbers are the same.

## image processing

The Raw images for the agents and animations were the wrong size, when processing they need bring them down by half
I used Magick to achieve this using this comand:

`` magick mogrify -filter point -resize 50% *.png``

### Cropping images

Before you use the Texture pack all images need to be cropped into individual sprint files.

I have written a python script to do this, or you use magick and apply bulk changes, you can find the script here:


``./assets/scripts/CropSprites.py``

You will have to make changes to the script when dealing with different size images and locations. I havent had time to customise this yet.
if you navigate the assets directory you can run this script from the command line there:

`` python scripts/CropSprites.py ``


## Texture Packer

you can find the Texture Packer functions here:

``./lwjgl3/build.gradle``

each of the functions will show which directories are being targetted and where the output is going.

### Texture Atlas

All sprites are sorted into a single atlas file.
when you add new atlases you need to add the to the main atlas file located here:

``./assets/model/textures/atlas.json``

