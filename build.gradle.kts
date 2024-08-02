plugins {
    id("java")
}

group = "com.accenture"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://maven.geotoolkit.org")
    mavenCentral()

    flatDir {
        dirs("lib")
    }

}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    val ojdbcPath = "lib/ojdbc8.jar"
    implementation(files(ojdbcPath))
    // https://mvnrepository.com/artifact/org.projectlombok/lombok
    compileOnly ("org.projectlombok:lombok:1.18.30")
    annotationProcessor ("org.projectlombok:lombok:1.18.30")

    // https://mvnrepository.com/artifact/org.apache.commons/commons-configuration2
    implementation("org.apache.commons:commons-configuration2:2.9.0")
    // https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
    implementation("org.apache.commons:commons-lang3:3.14.0")
    // https://mvnrepository.com/artifact/commons-logging/commons-logging
    implementation("commons-logging:commons-logging:1.3.0")
    // https://mvnrepository.com/artifact/org.apache.commons/commons-math3
    implementation("org.apache.commons:commons-math3:3.6.1")
    // https://mvnrepository.com/artifact/org.apache.commons/commons-text
    implementation("org.apache.commons:commons-text:1.11.0")
    implementation ("commons-codec:commons-codec:1.15")
    // https://mvnrepository.com/artifact/org.apache.pdfbox/fontbox
    implementation("org.apache.pdfbox:fontbox:3.0.1")
    // https://mvnrepository.com/artifact/org.icepdf.os/icepdf-core
    implementation("org.icepdf.os:icepdf-core:6.2.2")


    // https://mvnrepository.com/artifact/org.icepdf.os/icepdf-viewer
    implementation("org.icepdf.os:icepdf-viewer:6.2.2")


    // https://mvnrepository.com/artifact/org.apache.pdfbox/pdfbox
    implementation("org.apache.pdfbox:pdfbox:3.0.1")
    implementation ("org.apache.logging.log4j:log4j-core:2.14.1") // Use the latest version
    // implementation ("com.microsoft.sqlserver:mssql-jdbc:9.2.1.jre15")// for jdbc connection
    // implementation ("javax.swing:javax.swing-api:1.0")
    // implementation ("com.oracle.database.jdbc:ojdbc8:19.8.0.0")


    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    testCompileOnly ("org.projectlombok:lombok:1.18.30")
    testAnnotationProcessor ("org.projectlombok:lombok:1.18.30")

}


tasks.test {
    useJUnitPlatform()
}