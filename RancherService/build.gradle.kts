import ru.tinkoff.academy.tasks.LoadGardensTask
import ru.tinkoff.academy.tasks.LoadRancherDevDataTask
import java.nio.file.Path

tasks.register<LoadRancherDevDataTask>("loadDevData") {
    sqlFile = Path.of(projectDir.path, "..", "dev", "scripts", "Python", "users_data.sql")
}

tasks.register<LoadGardensTask>("loadGardens") {

}
