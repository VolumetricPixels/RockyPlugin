RockyPlugin
===========
RockyPlugin is a plugin for Bukkit Minecraft that's designed to increase functionality, efficiency, and overall usability for the development community.

Visit our [website][Website] or get support on our [forums][Forums].  

## Link
* MassiveMinecraft - <www.massiveminecraft.com> - For supplied us the test server to be able to test Rocky!.
* GamerArg - <www.gamerarg.com.ar> - My game community, main server is already using Rocky!.

## The License
RockyPlugin is licensed under the [GNU Lesser General Public License Version 3][License].

Copyright (c) 2012 VolumetricPixels <<http://www.volumetricpixels.com/>>  
[![VolumetricPixels][Author Logo]][Website]

## Getting the Source
The latest and greatest source can be found on [GitHub].  
Download the latest builds from [Jenkins]. 

## Compiling the Source
RockyPlugin uses Maven to handle its dependencies.

* Install [Maven 2 or 3](http://maven.apache.org/download.html)  
* Checkout this repo and run: `mvn clean install`

## Using with Your Project
For those using [Maven](http://maven.apache.org/download.html) to manage project dependencies, simply include the following in your pom.xml:

    <dependency>
        <groupId>com.volumetricpixels</groupId>
        <artifactId>rockyplugin</artifactId>
        <version>1.5.2-R0.2-SNAPSHOT</version>
    </dependency>

## Coding and Pull Request Conventions
* Generally follow the Oracle coding standards.
* No spaces, only tabs for indentation.
* No trailing whitespaces on new lines.
* 200 column limit for readability.
* Pull requests must compile, work, and be formatted properly.
* Sign-off on ALL your commits - this indicates you agree to the terms of our license.
* No merges should be included in pull requests unless the pull request's purpose is a merge.
* Number of commits in a pull request should be kept to *one commit* and all additional commits must be *squashed*.
* You may have more than one commit in a pull request if the commits are separate changes, otherwise squash them.

**Please follow the above conventions if you want your pull request(s) accepted.**

[Author Logo]: http://volumetricpixels.com/wp-content/uploads/2012/04/vp_concept2_6.png
[License]: http://www.gnu.org/licenses/lgpl.html
[Website]: http://www.volumetricpixels.com
[Forums]: http://volumetricpixels.com/forums/
[GitHub]: https://github.com/VolumetricPixels/RockyPlugin
[Jenkins]: http://ci.massiveminecraft.com/job/RockyPlugin/
