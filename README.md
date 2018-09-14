PlexMedia
==================
[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/9d71713560374c938dba8a476ce8debf)](https://www.codacy.com/app/maksim-m/Popular-Movies-App) [![Build Status](https://travis-ci.org/maksim-m/Popular-Movies-App.svg?branch=master)](https://travis-ci.org/maksim-m/Popular-Movies-App) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/4fad0c93fd3749d690571a7a728ce047)](https://www.codacy.com/app/piyushguptaece/hypertrack-live-android?utm_source=github.com&utm_medium=referral&utm_content=hypertrack/hypertrack-live-android&utm_campaign=badger) [![Slack Status](http://slack.hypertrack.com/badge.svg)](http://slack.hypertrack.com) [![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-HyperTrack%20Live-brightgreen.svg?style=flat)](https://android-arsenal.com/details/3/5754) [![Open Source Love](https://badges.frapsoft.com/os/v1/open-source.svg?v=103)](https://opensource.org/licenses/MIT) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Plex is the best leading subscription service for watching TV episodes and movies on your phone.  
This plex mobile application delivers the best experience anywhere, anytime.  Get the free app as a part of your plex membership and you can instantly watch thousands of TV episodes &amp; movies on your phone.   If you are not a plex member sign up for plex and start enjoying immediately on your phone with our one-month free trial.


**Features:**

- Discover the most popular, the highest rated and the most rated movies
- Watch movie trailers and teasers
- Read reviews from other users
- Mark movies as favorites
- Search for movies
- Offline work
- Material design
- UI optimized for phone and tablet

**Download:**

You can download APK [on releases page][5].




======================================================================
+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

## Screenshots

<img src="https://raw.githubusercontent.com/PlexMediaInc/Plex-Design/master/device.png" width="250"><img src="https://raw.githubusercontent.com/PlexMediaInc/Plex-Design/master/device-2018-03-01-170340.png" width="250"><img src="https://raw.githubusercontent.com/PlexMediaInc/Plex-Design/master/device-2018-03-01-170358.png" width="250"><img src="https://raw.githubusercontent.com/PlexMediaInc/Plex-Design/master/device-2018-03-12-024225.png" width="250"><img src="https://raw.githubusercontent.com/PlexMediaInc/Plex-Design/master/device-2018-03-12-024251.png" width="250"><img src="https://raw.githubusercontent.com/PlexMediaInc/Plex-Design/master/device-2018-03-12-023849.png" width="250"><img src="https://raw.githubusercontent.com/PlexMediaInc/Plex-Design/master/30652486_1679086142173039_3874851981860274176_n.jpg" width="250"><img src="https://raw.githubusercontent.com/PlexMediaInc/Plex-Design/master/30623738_1679086555506331_7054340864348258304_n.jpg" width="250"><img src="https://raw.githubusercontent.com/PlexMediaInc/Plex-Design/master/30652486_1679086142173039_3874851981860274176_n3.jpeg" width="250">


Developer setup
---------------

### Requirements

- Java 8
- Latest version of Android SDK and Android Build Tools

### API Key

The app uses themoviedb.org API to get movie information and posters. You must provide your own [API key][1] in order to build the app.

Just put your API key into `~/.gradle/gradle.properties` file (create the file if it does not exist already):

```gradle
MY_MOVIE_DB_API_KEY="abc123"
```

### Building

You can build the app with Android Studio or with `./gradlew assembleDebug` command.

### Testing

This project integrates a combination of [local unit tests][2], [instrumented tests][3] and [code analysis tools][4].

Just run `build.sh` to ensure that project code is valid and stable.
This will run local unit tests on the JVM, instrumented tests on connected device (or emulator) and analyse code with Checkstyle, Findbugs and PMD.


## Versioning

For transparency and insight into our release cycle, and for striving to maintain backward compatibility, PlexMedia will be maintained according to the [Semantic Versioning](http://semver.org/) guidelines as much as possible.

Releases will be numbered with the following format:

`<major>.<minor>.<patch>-<build>`

Constructed with the following guidelines:

* A new *major* release indicates a large change where backward compatibility is broken.
* A new *minor* release indicates a normal change that maintains backward compatibility.
* A new *patch* release indicates a bugfix or small change which does not affect compatibility.
* A new *build* release indicates this is a pre-release of the version.


***

If you distribute a copy or make a fork of the project, you have to credit this project as the source.

This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/ .

***


License
-------

    Copyright 2017 PlexMedia,Inc

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[1]: https://www.themoviedb.org/documentation/api
[2]: app/src/test/
[3]: app/src/androidTest/
[4]: quality/
[5]: https://github.com/mohamedebrahim96/PlexMedia/raw/master/version/release/app-release.apk
