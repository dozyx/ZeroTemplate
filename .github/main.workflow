workflow "build app" {
  on = "push"
  resolves = ["app 构建"]
}

action "app 构建" {
  uses = "gradle"
  secrets = ["GITHUB_TOKEN"]
  runs = "gradlew app:assembleDevelopmentRelease"
}
