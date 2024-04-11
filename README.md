![img_logo_big_filled.png](docs%2Fimg%2Fimg_logo_big_filled.png)

# YABD

![Gradle Plugin Portal Version](https://img.shields.io/gradle-plugin-portal/v/io.github.andrew-malitchuk.yabd)

## Overview

__YABD__ - Yet Another Build Dispatcher - a handy Gradle plugin for automating build distribution
to Slack, Telegram, and Jira. It simplifies the process of sharing build artifacts and updates with
your team members and stakeholders, enhancing collaboration and communication in your development
workflows.

## Features

- Automate build distribution to Slack, Telegram, and Jira.
- Customize distribution channels, messages, and attachments.
- Seamless integration with Gradle projects.
- <BuildVariant>ging options for easy troubleshooting.

## Installation

Apply the plugin in your `com.android.application` module and configure plugin via DSL:

<details open><summary>Kotlin</summary>

```kt
plugins {
    id("com.android.application")
    id("io.github.andrew-malitchuk.yabd") version "0.0.1-a.2"
}
```

</details>

<details><summary>Groovy</summary>

```groovy
plugins {
    id 'com.android.application'
    id 'io.github.andrew-malitchuk.yabd' version '0.0.1-a.2'
}
```

</details>

## Usage

__N.B.__ 

`<BuildVariant>` - is your current buildVariant, f.e. `debug`, `release`, `stageDebug` etc.

You may check it in the _Build -> Select Build Variant_.

### Telegram

`sendToTelegramTask<BuildVariant>` - send artifact to the Telegram chat;

```shell
./gradlew sendToTelegramTaskDebug
./gradlew sendToTelegramTaskStageDebug
```

`telegramCommentTask<BuildVariant>` - send some message to the Telegram. 

Parameters:

- `--message` - your message.

```shell
./gradlew telegramCommentTaskDebug --message="Lorem Ipsum"
./gradlew telegramCommentTaskStageDebug --message="Lorem Ipsum"
```

### Slack

`shareOnSlack<BuildVariant>` - send artifact to the Slack channel;

```shell
./gradlew shareOnSlackDebug
./gradlew shareOnSlackStageDebug
```

`slackComment<BuildVariant>`  - send some message to the Slack channel.

Parameters:

- `--message` - your message.

```shell
./gradlew slackCommentDebug --message="Lorem Ipsum"
./gradlew slackCommentStageDebug --message="Lorem Ipsum"
```

### Jira

attachToJiraTicket<BuildVariant> 
jiraComment<BuildVariant> 
jiraUpload<BuildVariant> 

## Configuration


### Secrets 

To use yours secrets (api keys, tokens etc) securely, please, store them in `local.properties` and 
fetch them in `build.gradle.kts` in follow way:

```kotlin
yabd {
    val keystoreFile = project.rootProject.file("local.properties")
    val properties = Properties()
    properties.load(keystoreFile.inputStream())

    telegram {
        chatId = properties["chatId"] as String
        token = properties["telegramToken"] as String
    }
    ...
```

Of course, you need to save your secrets in `local.properties` previously:

```shell
chatId=123
telegramToken=foobar
```

## Troubleshooting

Encountering issues while using YABD? Check out the troubleshooting section for common problems
and solutions. If you still need assistance, feel free to reach out to the YABD community
for support.

## Contributing

I welcome contributions from the community to help improve YABD. Whether you want to report a bug,
suggest a new feature, or submit a pull request, follow the contribution guidelines outlined in the
project's repository. Together, we can make YABD even better.

## TODO

- [ ] fix commands for leaving comments;
- [ ] simplify jira commands.

## License

MIT License

```
Copyright (c) [2024] [Andrew Malitchuk]

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```