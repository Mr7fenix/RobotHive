plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.0.13"
}

javafx {
    version = "21"
    modules("javafx.controls", "javafx.fxml")
}

application{
    mainClass.set("it.unicam.cs.pa.ma114110.Main")
}
