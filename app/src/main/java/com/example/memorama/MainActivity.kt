import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.memorama.R

class MainActivity : AppCompatActivity() {
    private val pairs = mapOf(
        R.id.image1 to R.drawable.blonde,
        R.id.image3 to R.drawable.camino_del_amor,
        R.id.image6 to R.drawable.endless,
        R.id.image8 to R.drawable.testig,
        R.id.image10 to R.drawable.mac
    )

    private var selectedImageView: ImageView? = null
    private var matchedPairs = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageIds = listOf(
            R.id.image1, R.id.image3, R.id.image6, R.id.image8, R.id.image10,
            R.id.image2, R.id.image5, R.id.image7, R.id.image9, R.id.image11
        )

        for (imageId in imageIds) {
            val imageView = findViewById<ImageView>(imageId)
            imageView.setBackgroundColor(Color.BLACK)
            imageView.setOnClickListener {
                try {
                    handleImageViewClick(imageView)
                } catch (e: Exception) {
                    e.printStackTrace()

                }
            }
        }
    }

    private fun handleImageViewClick(imageView: ImageView?) {
        try {

            if (imageView?.tag == "revealed") {

                return
            }


            val imageResource = getImageId(imageView?.id ?: 0)


            imageView?.setImageResource(imageResource)
            imageView?.tag = "revealed"


            if (selectedImageView == null) {
                selectedImageView = imageView
            } else {

                val selectedImageResource = getImageId(selectedImageView!!.id)


                if (pairs[selectedImageView!!.id] == imageResource ||
                    (pairs.containsKey(imageView?.id) && pairs[imageView?.id] == selectedImageResource)
                ) {

                    matchedPairs++


                    if (matchedPairs == pairs.size) {
                        showCongratulationsDialog()
                    }
                } else {

                    imageView?.setImageResource(R.drawable.card_back)
                    imageView?.tag = null
                    selectedImageView?.setImageResource(R.drawable.card_back)
                    selectedImageView?.tag = null
                }

                selectedImageView = null
            }
        } catch (e: Exception) {

            e.printStackTrace()
            println("Error al manejar el click de la imagen: ${e.message}")


            imageView?.setImageResource(R.drawable.card_back)
            imageView?.tag = null
            selectedImageView?.setImageResource(R.drawable.card_back)
            selectedImageView?.tag = null


            selectedImageView = null
        }
    }

    private fun getImageId(viewId: Int): Int {
        return when (viewId) {
            R.id.image1, R.id.image2 -> R.drawable.blonde
            R.id.image3, R.id.image5 -> R.drawable.camino_del_amor
            R.id.image6, R.id.image7 -> R.drawable.endless
            R.id.image8, R.id.image9 -> R.drawable.testig
            R.id.image10, R.id.image11 -> R.drawable.mac
            else -> R.drawable.card_back
        }
    }

    private fun showCongratulationsDialog() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle(":)")
        builder.setMessage("¡PARES ENCONTADOS!")
        builder.setPositiveButton("Reiniciar juego") { dialog, _ ->

            resetGame()
            dialog.dismiss()
        }
        builder.setCancelable(false)


        val dialog = builder.create()
        dialog.show()
    }

    private fun resetGame() {

        selectedImageView = null
        matchedPairs = 0

        val imageIds = listOf(
            R.id.image1, R.id.image3, R.id.image6, R.id.image8, R.id.image10,
            R.id.image2, R.id.image5, R.id.image7, R.id.image9, R.id.image11
        )

        for (imageId in imageIds) {
            val imageView = findViewById<ImageView>(imageId)
            imageView.setImageResource(R.drawable.card_back)
            imageView.tag = null
        }
    }
}
