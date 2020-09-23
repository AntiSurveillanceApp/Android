## Installation

You must have node installed: [Install NodeJS](https://nodejs.org/en/download/).

Clone repository and change directory to it

``` 
git clone https://github.com/AntiSurveillanceApp/Android.git
cd /app
```

Install nodemon
```
npm install -g nodemon
```

Install all dependencies needed
```
npm install
```

## Run the server
This project needs nodemon to run and watch file changes. The command is:
```
nodemon Server.js
```

In case we need to run it as background service. Install and run:

```
npm install -g 
forever start -c nodemon Server.js
```

## Configruation
You can find default config in `nodemon.json`. That's just simple:
```
{
  "env" : {
    "RUN_MODE"    : "dev",
    "PORT"        : 4333
  }
}
```
Since the nodemon service could not watch itself config, you need to restart it.

## Run React Native
You need react-native installed: [React Native](https://facebook.github.io/react-native/docs/getting-started.html)

Follow these steps to run the project:

```
cd client/react-native
npm install
react-native link
react-native run-android
```

## License

    Copyright 2018 Fernando Cejas

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
