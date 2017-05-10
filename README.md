Render mandelbrot with Java

The project is split up in a class which generates BufferedImage's of the mandelbrot set. Here all the math is done.
Two seperate classes uses different frameworks to display and interact with the image. Left click zooms, right click zooms out and middle mousebutton moves the view.
Currently the most mature of the two is the one using javaFX.

There is also an ant build script included, which compiles the javaFX solution to a jar executable.

Project has been developed in eclipse and related project files are included. Still need to test if this is enough to make the project 'portable'.
