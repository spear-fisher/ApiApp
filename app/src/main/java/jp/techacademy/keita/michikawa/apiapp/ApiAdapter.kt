package jp.techacademy.keita.michikawa.apiapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ApiAdapter(private val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    // 取得したJsonデータを解析し、Shop型オブジェクトとして生成したものを格納するリスト
    private val items = mutableListOf<Shop>()

    // 表示リスト更新時に呼び出すメソッド
    fun refresh(list: List<Shop>) {
        items.apply {
            clear() // items を 空にする
            addAll(list) // itemsにlistを全て追加する
        }
        notifyDataSetChanged() // recyclerViewを再描画させる
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // ViewHolderを継承したApiItemViewHolderオブジェクトを生成し戻す
        return ApiItemViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_favorite, parent, false))
    }

    // ViewHolderを継承したApiItemViewHolderクラスの定義
    class ApiItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        // レイアウトファイルからidがrootViewのConstraintLayoutオブジェクトを取得し、代入
        val rootView : ConstraintLayout = view.findViewById(R.id.rootView)
        // レイアウトファイルからidがnameTextViewのCTextViewオブジェクトを取得し、代入
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        // レイアウトファイルからidがimageViewのImageViewオブジェクトを取得し、代入
        val imageView: ImageView = view.findViewById(R.id.imageView)
        // レイアウトファイルからidがfavoriteImageViewのImageViewオブジェクトを取得し、代入
        val favoriteImageView: ImageView = view.findViewById(R.id.favoriteImageView)
    }

    override fun getItemCount(): Int {
        // itemsプロパティに格納されている要素数を返す
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ApiItemViewHolder) {
            // 生成されたViewHolderがApiItemViewHolderだったら。。。
            updateApiItemViewHolder(holder, position)
        }// {
        // 別のViewHolderをバインドさせることが可能となる
        // }
    }

    private fun updateApiItemViewHolder(holder: ApiItemViewHolder, position: Int) {
        // 生成されたViewHolderの位置を指定し、オブジェクトを代入
        val data = items[position]
        holder.apply {
            rootView.apply {
                // 偶数番目と奇数番目で背景色を変更させる
                setBackgroundColor(ContextCompat.getColor(context,
                    if (position % 2 == 0) android.R.color.white else android.R.color.darker_gray))
            }
            // nameTextViewのtextプロパティに代入されたオブジェクトのnameプロパティを代入
            nameTextView.text = data.name
            // Picassoライブラリを使い、imageViewにdata.logoImageのurlの画像を読み込ませる
            Picasso.get().load(data.logoImage).into(imageView)
            // 白抜きの星マークの画像を指定
            favoriteImageView.setImageResource(R.drawable.ic_star_border)
        }
    }
}