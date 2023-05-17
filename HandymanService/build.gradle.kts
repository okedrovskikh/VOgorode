import ru.tinkoff.academy.tasks.LoadHandymanDevDataTask
import ru.tinkoff.academy.tasks.LoadWorkersTask
import java.nio.file.Path

tasks.register<LoadHandymanDevDataTask>("loadDevData") {
    sqlFile = Path.of(projectDir.path, "..", "dev", "scripts", "Python", "users_data.sql")
}

tasks.register<LoadWorkersTask>("loadWorkers") {

}
