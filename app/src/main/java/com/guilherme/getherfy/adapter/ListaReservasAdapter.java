package com.guilherme.getherfy.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.guilherme.getherfy.ui.AbasActivity;
import com.guilherme.getherfy.ui.fragments.abas.AtualizaLista;
import com.guilherme.getherfy.ui.fragments.abas.ListaReservasFragment;
import com.guilherme.getherfy.httpService.HttpServiceCancelarReserva;
import com.guilherme.getherfy.models.Reserva;
import com.guilherme.presentation.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ListaReservasAdapter extends BaseAdapter {
    private List<Reserva> reservas;
    private Context context;
    private static int selecionado = -1;
    public AtualizaLista atualizaLista = null;
    private int idReserva;
    private String usuarioLogado = AbasActivity.idUsuario;
    private int logadoId = Integer.parseInt(usuarioLogado);



    public ListaReservasAdapter(List<Reserva> reservas, Context context) {
        this.reservas = reservas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return reservas.size();

    }

    @Override
    public Reserva getItem(int position) {
        return reservas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return reservas.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {


        final View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_reserva_layout, parent, false);
        final Reserva reserva = reservas.get(position);

        TextView data = viewCriada.findViewById(R.id.fragment_item_reserva_dia);
        TextView horaInicio = viewCriada.findViewById(R.id.fragment_item_reserva_hora_inicio);
        TextView horaFim = viewCriada.findViewById(R.id.fragment_item_reserva_hora_fim);

        TextView descricao = viewCriada.findViewById(R.id.fragmente_item_reserva_assunto);
        descricao.setText(reserva.getDescricao());
        TextView organizador = viewCriada.findViewById(R.id.fragment_item_reserva_organizador);
        organizador.setText(reserva.getNomeOrganizador());
        TextView localizacao = viewCriada.findViewById(R.id.fragment_item_reserva_localizacao);
        localizacao.setText(reserva.getNomeSala());


        CardView cardView = viewCriada.findViewById(R.id.item_reserva_cardview);
        final LinearLayout linearLayout = viewCriada.findViewById(R.id.item_reserva_layout_options);

        configuraDataHora(reserva, data, horaInicio, horaFim);

        if(reserva.getOrganizador()==logadoId) {
            if (position == selecionado) {
                linearLayout.setVisibility(View.VISIBLE);

                ImageButton btnRemover = viewCriada.findViewById(R.id.fragment_item_reserva_remover);
                ImageButton btnCancelar = viewCriada.findViewById(R.id.fragment_item_reserva_cancelar);


                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e("CANCELAR", "Click");

                        linearLayout.setVisibility(View.GONE);
                    }
                });


                btnRemover.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        linearLayout.setVisibility(View.GONE);
                        String idReserva = String.valueOf(reserva.getId());
                        reserva.setAtivo(false);

                        requestRemover(idReserva, viewCriada);

                    }
                });
            } else {
                linearLayout.setVisibility(View.GONE);

            }

            if(position == selecionado-1){
                linearLayout.setVisibility(View.GONE);

            }
        }

        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                selecionado = position;
                Log.e("Adapter", "Valor: " + selecionado);
                atualizaLista.atualizarLista(true);

                return false;
            }

        });

        return viewCriada;
    }

    public void requestRemover(String idReserva, View viewCriada) {
        try {
            String respostaStr = new HttpServiceCancelarReserva().execute(idReserva).get();
            Toast.makeText(viewCriada.getContext(), respostaStr, Toast.LENGTH_LONG).show();
            atualizaLista.atualizarLista(true);

            ListaReservasFragment.setPrecisaConexao(true);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void configuraDataHora(Reserva reserva, TextView data, TextView horaInicio, TextView horaFim) {
        SimpleDateFormat dateFormatUS = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormatBR = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat hourFormatRecebe = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");

        for (int i = 0; i < 2; i++) {
            String dataHora;

            if (i == 0) {
                dataHora = reserva.getDataHoraInicio();
            } else {
                dataHora = reserva.getDataHoraFim();
            }
            String[] dataSplitada = dataHora.split("T");
            String[] horaInicioSplitada = dataSplitada[1].split("Z");


            try {
                Date dateParseado = dateFormatUS.parse(dataSplitada[0]);
                String dateStr = dateFormatBR.format(dateParseado);
                Date horaInicioParseada = hourFormatRecebe.parse(horaInicioSplitada[0]);
                String horaStr = hourFormat.format(horaInicioParseada);

                System.out.println(dataSplitada[1]);


                data.setText(dateStr);
                System.out.println(data.getText().toString());

                if (i == 0) {
                    horaInicio.setText(horaStr + " - ");
                } else {
                    horaFim.setText(horaStr);
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

}


