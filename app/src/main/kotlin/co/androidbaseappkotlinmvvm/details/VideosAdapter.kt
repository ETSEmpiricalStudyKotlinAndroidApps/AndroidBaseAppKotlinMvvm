package co.androidbaseappkotlinmvvm.details

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.androidbaseappkotlinmvvm.R
import co.androidbaseappkotlinmvvm.entities.Video
import kotlinx.android.synthetic.main.videos_adapter_row.view.*

class VideosAdapter(private val videos: List<Video>, private val callback: (Video) -> (Unit)) : RecyclerView.Adapter<VideosAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.videos_adapter_row, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder?.bind(videos[position], callback)
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(video: Video, callback: (Video) -> Unit) {
            with(itemView) {
                video_adapter_name.text = video.name
                setOnClickListener { callback(video) }
            }
        }

    }
}