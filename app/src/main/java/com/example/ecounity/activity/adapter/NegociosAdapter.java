package com.example.ecounity.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecounity.R;
import com.example.ecounity.activity.model.Negocios;

import java.util.ArrayList;
import java.util.List;

public class NegociosAdapter extends RecyclerView.Adapter<NegociosAdapter.NegociosViewHolder> {

    private List<Negocios> listaNegocios;
    private Context context;
    private static final int ANIMATION_DURATION = 3000; // 3 segundos

    public NegociosAdapter(Context context) {
        this.listaNegocios = new ArrayList<>();
        this.context = context;
    }

    public void setListaNegocios(List<Negocios> listaNegocios) {
        this.listaNegocios = listaNegocios;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NegociosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vitrine, parent, false);
        return new NegociosViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NegociosViewHolder holder, int position) {
        Negocios negocio = listaNegocios.get(position);
        holder.nomeTextView.setText(negocio.getNome());

        // Carregar a imagem do logotipo usando Glide
        Glide.with(holder.itemView.getContext())
                .load(negocio.getLogotipo())
                .into(holder.logotipoImageView);

        // Configurar o ViewAnimator com as imagens dos produtos
        if (negocio.getImagens() != null && !negocio.getImagens().isEmpty()) {
            holder.viewAnimator.removeAllViews(); // Limpa todas as views existentes
            for (String imageUrl : negocio.getImagens()) {
                ImageView imageView = new ImageView(holder.itemView.getContext());
                Glide.with(holder.itemView.getContext())
                        .load(imageUrl)
                        .into(imageView);
                holder.viewAnimator.addView(imageView); // Adiciona a nova ImageView ao ViewAnimator
            }
            // Iniciar a animação de transição de imagens
            startImageAnimation(holder.viewAnimator);
        }

        holder.descricaoTextView.setText(negocio.getDescricao());
        holder.siteTextView.setText(negocio.getSite());
        holder.emailTextView.setText(negocio.getEmail());
        holder.phoneTextView.setText(negocio.getTelefone());
        holder.nome_produtoTextView.setText(negocio.getProduto());
        holder.descricao_produtoTextView.setText(negocio.getDescricao_produto());

        // Formatar o preço para exibir vírgula ao invés de ponto e remover zeros desnecessários
        String precoString = String.format("%.2f", negocio.getPreco()).replace(".", ",");
        if (precoString.endsWith(",00")) {
            precoString = precoString.substring(0, precoString.length() - 3);
        }
        holder.precoTextView.setText(precoString);

        // Adiciona o clique no item para abrir o Google Maps
        holder.itemView.setOnClickListener(v -> {
            String address = negocio.getEndereco(); // Certifique-se que você tem um método getEndereco() na classe Negocios
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(mapIntent);
            }
        });

        // Ação do botão Site
        holder.siteButton.setOnClickListener(v -> {
            String url = negocio.getSite();
            if (url != null && !url.isEmpty()) {
                // Verifique se o URL começa com "http://" ou "https://"
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    url = "http://" + url;
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                context.startActivity(intent); // Chamada direta do método startActivity
            }
        });

        // Ação do botão Enviar e-mail
        holder.emailButton.setOnClickListener(v -> {
            String email = negocio.getEmail();
            if (email != null && !email.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null));
                context.startActivity(Intent.createChooser(intent, "Enviar e-mail..."));
            }
        });

        // Ação do botão Ligar
        holder.ligarButton.setOnClickListener(v -> {
            String phone = negocio.getTelefone();
            if (phone != null && !phone.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaNegocios.size();
    }

    private void startImageAnimation(ViewAnimator viewAnimator) {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int displayedChild = viewAnimator.getDisplayedChild();
                int childCount = viewAnimator.getChildCount();
                int nextChild = (displayedChild + 1) % childCount;
                viewAnimator.setDisplayedChild(nextChild);
                handler.postDelayed(this, ANIMATION_DURATION);
            }
        };
        handler.post(runnable);
    }

    public static class NegociosViewHolder extends RecyclerView.ViewHolder {

        public TextView nomeTextView;
        public ImageView logotipoImageView;
        public TextView descricaoTextView;
        public TextView siteTextView;
        public TextView emailTextView;
        public TextView phoneTextView;
        public TextView enderecoTextView;
        public TextView nome_produtoTextView;
        public TextView descricao_produtoTextView;
        public TextView precoTextView;
        public ViewAnimator viewAnimator;
        public Button siteButton;
        public Button emailButton;
        public Button ligarButton;

        public NegociosViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeTextView = itemView.findViewById(R.id.txtTituloVitrine);
            logotipoImageView = itemView.findViewById(R.id.imageViewLogotipo);
            descricaoTextView = itemView.findViewById(R.id.txtDescricaoNegocio);
            siteTextView = itemView.findViewById(R.id.txtSite);
            emailTextView = itemView.findViewById(R.id.txtEmail);
            phoneTextView = itemView.findViewById(R.id.txtPhone);
            enderecoTextView=itemView.findViewById(R.id.txtEnderecoNegocio);
            nome_produtoTextView = itemView.findViewById(R.id.txtNomeProduto);
            descricao_produtoTextView = itemView.findViewById(R.id.txtDescricaoProduto);
            precoTextView = itemView.findViewById(R.id.txtPreco);
            viewAnimator = itemView.findViewById(R.id.ImagensCarrosel);
            siteButton = itemView.findViewById(R.id.btnsite);
            emailButton = itemView.findViewById(R.id.btnemail);
            ligarButton = itemView.findViewById(R.id.btnligar);
        }
    }
}
