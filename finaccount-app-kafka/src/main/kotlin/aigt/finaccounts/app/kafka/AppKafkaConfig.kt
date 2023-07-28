package aigt.finaccounts.app.kafka

class AppKafkaConfig(
    val kafkaHosts: List<String> = KAFKA_HOSTS,
    val kafkaGroupId: String = KAFKA_GROUP_ID,
    val kafkaTopicInV1Jvm: String = KAFKA_TOPIC_IN_V1_JVM,
    val kafkaTopicOutV1Jvm: String = KAFKA_TOPIC_OUT_V1_JVM,
    val kafkaTopicInV1Kmp: String = KAFKA_TOPIC_IN_V1_KMP,
    val kafkaTopicOutV1Kmp: String = KAFKA_TOPIC_OUT_V1_KMP,
) {
    companion object {
        const val KAFKA_HOST_VAR = "KAFKA_HOSTS"
        const val KAFKA_TOPIC_IN_V1_JVM_VAR = "KAFKA_TOPIC_IN_V1_JVM"
        const val KAFKA_TOPIC_OUT_V1_JVM_VAR = "KAFKA_TOPIC_OUT_V1_JVM"
        const val KAFKA_TOPIC_IN_V1_KMP_VAR = "KAFKA_TOPIC_IN_V1_KMP"
        const val KAFKA_TOPIC_OUT_V1_KMP_VAR = "KAFKA_TOPIC_OUT_V1_KMP"
        const val KAFKA_GROUP_ID_VAR = "KAFKA_GROUP_ID"

        val KAFKA_HOSTS by lazy {
            (System.getenv(KAFKA_HOST_VAR) ?: "").split("\\s*[,;]\\s*")
        }

        val KAFKA_GROUP_ID by lazy {
            System.getenv(KAFKA_GROUP_ID_VAR)
                ?: "finaccounts"
        }

        val KAFKA_TOPIC_IN_V1_JVM by lazy {
            System.getenv(KAFKA_TOPIC_IN_V1_JVM_VAR)
                ?: "finaccounts-in-v1-jvm"
        }

        val KAFKA_TOPIC_OUT_V1_JVM by lazy {
            System.getenv(KAFKA_TOPIC_OUT_V1_JVM_VAR)
                ?: "finaccounts-out-v1-jvm"
        }

        val KAFKA_TOPIC_IN_V1_KMP by lazy {
            System.getenv(KAFKA_TOPIC_IN_V1_KMP_VAR)
                ?: "finaccounts-in-v1-kmp"
        }

        val KAFKA_TOPIC_OUT_V1_KMP by lazy {
            System.getenv(KAFKA_TOPIC_OUT_V1_KMP_VAR)
                ?: "finaccounts-out-v1-kmp"
        }
    }
}
