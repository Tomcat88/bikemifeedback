package it.introini.bikemifeedback.config


enum class Parameter(val parameter: String, val default: Any) {
    LOG_LEVEL ("log.level", "DEBUG"),
    LOG_FILE  ("log.file", "bikemifeedback.log"),

    HTTP_PORT ("http.port", 8082),

    MONGO_DB_NAME              ("mongo.db.name", "bikemi_feedback"),
    MONGO_DB_HOST              ("mongo.db.host", "localhost"),
    MONGO_DB_PORT              ("mongo.db.port", 27017)
}