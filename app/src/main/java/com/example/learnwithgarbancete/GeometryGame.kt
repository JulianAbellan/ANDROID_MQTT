package com.example.learnwithgarbancete

import android.R.attr.bitmap
import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView


class GeometryGame : AppCompatActivity(){

    lateinit var star1: ImageView;
    lateinit var star2: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_geometry_game)
        star1 = findViewById(R.id.star1)
        star2 = findViewById(R.id.star2)

        val IMAGEVIEW_TAG = "icon bitmap"
        val d: Drawable = star1.getDrawable()
        var bitmap = Bitmap.createBitmap(d.intrinsicWidth, d.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        d.draw(canvas)

        val star1 = ImageView(this).apply {
            // Sets the bitmap for the ImageView from an icon bit map (defined elsewhere).
            setImageBitmap(bitmap)
            tag = IMAGEVIEW_TAG
            setOnLongClickListener { v ->
                // Create a new ClipData.
                // This is done in two steps to provide clarity. The convenience method
                // ClipData.newPlainText() can create a plain text ClipData in one step.

                // Create a new ClipData.Item from the ImageView object's tag.
                val item = ClipData.Item(v.tag as? CharSequence)

                // Create a new ClipData using the tag as a label, the plain text MIME type, and
                // the already-created item. This creates a new ClipDescription object within the
                // ClipData and sets its MIME type to "text/plain".
                val dragData = ClipData(
                    v.tag as? CharSequence,
                    arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN),
                    item
                )

                // Instantiate the drag shadow builder.
                val myShadow = MyDragShadowBuilder(this)

                // Start the drag.
                v.startDragAndDrop(
                    dragData,  // The data to be dragged
                    myShadow,  // The drag shadow builder
                    null,      // No need to use local data
                    0          // Flags (not currently used, set to 0)
                )

                // Indicate that the long-click was handled.
                true
            }
        }
    }
}