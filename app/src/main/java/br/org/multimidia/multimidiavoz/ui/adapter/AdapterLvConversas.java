package br.org.multimidia.multimidiavoz.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.org.multimidia.multimidiavoz.R;
import br.org.multimidia.multimidiavoz.domain.Conversa;
import br.org.multimidia.multimidiavoz.utils.ViewHolder;

/**
 * Created by jheimesilveira on 01/03/2016.
 */
public class AdapterLvConversas extends BaseAdapter {

    private Context context;
    private List<Conversa> list;

    /**
     * metodo defaut para view personalizado
     * @param context contexto
     * @param list lista com os objetos papeados pela interface ObjectAdapter
     */
    public AdapterLvConversas(Context context, List<Conversa> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.adapter_list_conversas, parent, false);
        }

        Conversa object = list.get(position);
        ViewHolder holder = (ViewHolder) view.getTag();

        TextView tvName;
        TextView tvDescription;
        TextView tvCategory;
        ImageView ivAddProfile;
        ImageView ivImg;

        if (holder == null) {
            ivAddProfile = (ImageView) view.findViewById(R.id.adapter_lv_view_profile_btn_add_user);
            tvName = (TextView) view.findViewById(R.id.adapter_lv_view_profile_tv_name);
            tvDescription = (TextView) view.findViewById(R.id.adapter_lv_view_profile_tv_description);
            tvCategory = (TextView) view.findViewById(R.id.adapter_lv_view_profile_tv_category);
            ivImg = (ImageView) view.findViewById(R.id.adapter_lv_view_profile_img);
            holder = new ViewHolder();

            holder.setProperty(tvName, "tvName");
            holder.setProperty(tvDescription, "tvDescription");
            holder.setProperty(tvCategory, "tvCategory");
            holder.setProperty(ivAddProfile, "ivAddProfile");
            holder.setProperty(ivImg, "ivImg");
            view.setTag(holder);
        }

        ivAddProfile = (ImageView) holder.getProperty("ivAddProfile");
        tvDescription = (TextView) holder.getProperty("tvDescription");
        tvCategory = (TextView) holder.getProperty("tvCategory");
        tvName = (TextView) holder.getProperty("tvName");
        ivImg = (ImageView) holder.getProperty("ivImg");

        if (object.getDtaInicio() != null && object.getDtaFinal() != null) {
            long total = object.getDtaFinal().getTime() - object.getDtaInicio().getTime();
            long m = total / 60 / 1000;
            long s = total%60;
            String txt = "";

            txt = ""+m+" minutos e "+ s +" segundos ";

            tvDescription.setText(txt);
        }
        if (object.getUsuarioDestino() != null) {
            tvName.setText(object.getUsuarioDestino());
        }

        return view;
    }
}
