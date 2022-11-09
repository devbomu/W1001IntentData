package kr.ac.kumoh.s20180100.w1001intentdata

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import kr.ac.kumoh.s20180100.w1001intentdata.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val keyName = "image"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSea.setOnClickListener(this)
        binding.btnTree.setOnClickListener(this)

        launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode != RESULT_OK) return@registerForActivityResult

            val result = it.data?.getIntExtra(ImageActivity.resultName, ImageActivity.NONE)
            val str = when (result) {
                ImageActivity.LIKE -> "좋아요"
                ImageActivity.DISLIKE -> "싫어요"
                else -> ""
            }
            val image = it.data?.getStringExtra(ImageActivity.imageName)
            when (image) {
                "sea" -> binding.btnSea.text = "바다 ($str)"
                "tree" -> binding.btnTree.text = "나무 ($str)"
            }
        }
    }

    override fun onClick(p0: View?) {
        val intent = Intent(this, ImageActivity::class.java)
        val value = when (p0?.id) {
            binding.btnSea.id -> "sea"
            binding.btnTree.id -> "tree"
            else -> return
        }
        intent.putExtra(keyName, value)
        launcher.launch(intent)
    }
}