package custom

import java.io.*

object FileHandler {
    fun <T : Serializable> writeObjectToFile(obj: T, file: File) {
        try {
            ObjectOutputStream(FileOutputStream(file)).use { stream ->
                stream.writeObject(obj)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    inline fun <reified T : Serializable> readFileToObject(file: File?): T? {
        if (file == null)
            return null

        return try {
            ObjectInputStream(FileInputStream(file)).use { stream ->
                stream.readObject() as? T
            }
        } catch (e: Exception) {
            null
        }
    }
}