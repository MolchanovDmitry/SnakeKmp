<h1 align="center">Kotlin multiplatform Snake</h1> 
<p align="center">
  <a href="#">
    <img src="https://img.shields.io/badge/android-7.10-blue?logo=android">
  </a>
  <a href="https://github.com/MolchanovDmitry/SnakeWearOS/actions/workflows/lint.yml">
    <img src="https://github.com/MolchanovDmitry/SnakeWearOS/actions/workflows/lint.yml/badge.svg">
  </a>
  <br>
  <a href="https://developer.android.com/jetpack/androidx/releases/wear-compose">
    <img src="https://img.shields.io/badge/Wear%20Compose-1.1.0--alpha07-yellow)">
  </a>
  <a href="https://github.com/MolchanovDmitry/SnakeWearOS/actions/workflows/web.yml">
    <img src="https://github.com/MolchanovDmitry/SnakeWearOS/actions/workflows/web.yml/badge.svg">
  </a>
  <br>
  <a href="https://www.jetbrains.com/lp/compose-mpp/">
    <img src="https://img.shields.io/badge/Compose%20Multiplatform%20Compose-1.2.0-yellow)">
  </a>
  <a href="https://github.com/MolchanovDmitry/SnakeWearOS/actions/workflows/wear.yml">
    <img src="https://github.com/MolchanovDmitry/SnakeWearOS/actions/workflows/wear.yml/badge.svg">
  </a>
</p>

Snake from childhood for the following platforms:
* [wear OS](#Wear-OS)
* [web](#Web)

# Wear OS
<div align="center">
  <img src="img/wear_snake_game.webp">
</div>

You can get the apk by running [the following github action](https://github.com/MolchanovDmitry/SnakeWearOS/actions/workflows/wear.yml).

Or build apk manually by running command below in root directory:

``
./gradlew :wear:assembleRelease
``

The output file will be located in the following directory `wear/build/outputs/apk/release/`. 

# Web
Adjust the field size and press `Start!`:
<div align="center">
  <img src="img/web_snake_settings.webp">
  <img src="img/web_snake_game.webp">
</div>

You can get the artefacts by running [the following github action](https://github.com/MolchanovDmitry/SnakeWearOS/actions/workflows/web.yml).

Or run game manually by running command below in root directory:
```
./gradlew :wear:assembleRelease
```


## License
```
Copyright 2022 Dmitry Molchanov

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.