package aplicacao.eventos;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ToolTipManager;
import javax.swing.event.AncestorEvent;
import metodos.YoutubeArquivo;

/**
 * Classe responsável pelos eventos menores.
 */
@SuppressWarnings("serial")
public class YoutubeEventosMenores extends YoutubeEventosPesquisa {

    @Override
    protected void txtLinkMouseClick(MouseEvent ev) {
        if (txtLink.getSelectionColor() == Color.lightGray
                || txtLink.getSelectionColor() == Color.gray) {
            txtLink.setSelectionEnd(0);
            txtLink.setSelectionColor(Color.white);
            txtLink.setSelectedTextColor(Color.black);
            return;
        }
        txtLink.selectAll();
        if (!noturno) {
            txtLink.setSelectionColor(Color.lightGray);
        } else {
            txtLink.setSelectionColor(Color.gray);
        }
        txtLink.setSelectedTextColor(Color.white);
    }

    @Override
    protected void txtPesquisaMouseClick(MouseEvent ev) {
        if (txtPesquisa.getSelectionColor() == Color.lightGray
                || txtPesquisa.getSelectionColor() == Color.gray) {
            txtPesquisa.setSelectionEnd(0);
            txtPesquisa.setSelectionColor(Color.white);
            txtPesquisa.setSelectedTextColor(Color.black);
            return;
        }
        txtPesquisa.selectAll();
        if (!noturno) {
            txtPesquisa.setSelectionColor(Color.lightGray);
        } else {
            txtPesquisa.setSelectionColor(Color.gray);
        }
        txtPesquisa.setSelectedTextColor(Color.white);
    }

    @Override
    protected void lstPesquisaAncestor(AncestorEvent ev) {
        if (!lstPesquisa.isSelectionEmpty()) {
            index = lstPesquisa.getSelectedIndex();
            lstPesquisa.setToolTipText("<html>" + lstDescricao.get(index) + "</html>");
        }
    }

    @Override
    protected void lstPesquisaMouseClickItem(MouseEvent ev) {

        scheduleGetId = EXECUTOR.schedule(() -> {
            if (!lstPesquisa.isSelectionEmpty() && !lstPesquisa.isSelectedIndex(index)) {
                try {
                    disableRadioButtons();
                    menuBarRadioBtn.setVisible(false);
                    YoutubeArquivo r = new YoutubeArquivo("/tmp/resolutions", false);
                    r.deletar();
                    index = lstPesquisa.getSelectedIndex();
                    lstPesquisa.setToolTipText("<html>" + lstDescricao.get(index) + "</html>");
                    ToolTipManager.sharedInstance().mouseMoved(ev);
                    ToolTipManager.sharedInstance().setDismissDelay(6000);

                    if (!audio) {
                        link = links[index];
                        YoutubeArquivo res = new YoutubeArquivo(scriptRes, false);
                        res.criar(String.format("#!/usr/bin/python3\n"
                                + "import youtube_dl\n"
                                + "import os\n"
                                + "\n"
                                + "url = '%s'\n"
                                + "file = '/tmp/resolutions'\n"
                                + "v = youtube_dl.YoutubeDL()\n"
                                + "i = v.extract_info(url, download=False)\n"
                                + "f = i.get('formats', i)\n"
                                + "\n"
                                + "for d in f:\n"
                                + " for k,v in d.items():\n"
                                + "  if k == 'ext' and v == 'mp4':\n"
                                + "   with open(file, 'a' if os.path.isfile(file) else 'w') as res:\n"
                                + "    if d['acodec'] != 'none':\n"
                                + "     res.write(f'{d[\"format_id\"]} {d[\"format_note\"]}(+audio) vcodec: {d[\"vcodec\"]}\\n')\n"
                                + "    else:\n"
                                + "     res.write(f'{d[\"format_id\"]} {d[\"format_note\"]} vcodec: {d[\"vcodec\"]}\\n')\n",
                                link));
                        if (btnBaixa.isVisible()) {
                            btnBaixa.setVisible(false);
                        } else if (btnBaixa2.isVisible()) {
                            btnBaixa2.setVisible(false);
                        } else {
                            btnBaixa3.setVisible(false);
                        }

                        lblResultado.setText(TEXTOS.pegarTexto("label.resultado.resolucao"));
                        configurarCores();
                        String comando = CMD.comando("python3 " + scriptRes);
                        cancelScheduleBtnCancelPro();
                        radioBtnMenuItem1.setSelected(true);
                        resId = TEXTOS.pegarTexto("radio.menu.melhor.resolucao");

                        if (comando.contains("Traceback (most recent call last):")) {
                            disableRadioButtons();
                            menuBarRadioBtn.setVisible(false);
                            lblResultado.setText(TEXTOS.pegarTexto("label.resultado.resolucaofalhou"));
                            lblResultado.setForeground(CORES.pegarCorComBrilho(noturno, "Erros"));
                        }

                        if (!btnBaixa.isVisible() && scheduleDownload.isDone()) {
                            btnBaixa.setVisible(true);
                        } else if (!btnBaixa2.isVisible() && scheduleDownload2.isDone()) {
                            btnBaixa2.setVisible(true);
                        } else if (!btnBaixa3.isVisible() && scheduleDownload3.isDone()) {
                            btnBaixa3.setVisible(true);
                        }

                        List<String> resList = r.listar();

                        boolean isDone = false;

                        
                        for (int i = 0; i < resList.size(); i++) {
                            for (JRadioButtonMenuItem jRadioButtonMenuItem : radBtnList) {
                                if (!jRadioButtonMenuItem.isVisible()) {
                                    jRadioButtonMenuItem.setVisible(true);
                                    splitRes = resList.get(i).split(" ", -1);
                                    String splitText = (splitRes[1].equals("DASH")) ? splitRes[1] + " " + splitRes[2] + " " + splitRes[3] + " " + splitRes[4]
                                            : splitRes[1] + " " + splitRes[2] + " " + splitRes[3];
                                    jRadioButtonMenuItem.setText(splitText);
                                    jRadioButtonMenuItem.setToolTipText(TEXTOS.pegarTexto("tooltip.radio.menu.items"));
                                    if (i == 0) isDone =true;
                                    break;
                                }
                            }
                        }
                        
                        if (isDone) {
                            menuBarRadioBtn.setVisible(true);
                            lblResultado.setText(TEXTOS.pegarTexto("label.resultado.resolucaoconc"));
                            lblResultado.setForeground(CORES.pegarCorComBrilho(noturno, "Concluido"));
                        } else {
                            lblResultado.setText(TEXTOS.pegarTexto("label.resultado.resolucaocancelado"));
                            lblResultado.setForeground(CORES.pegarCorComBrilho(noturno, "Avisos"));
                        }
                    }
                } catch (IOException ex) {
                    lblResultado.setText(TEXTOS.pegarTexto("label.resultado.resolucaofalhou"));
                    lblResultado.setForeground(CORES.pegarCorComBrilho(noturno, "Erros"));
                    ex.printStackTrace();
                }
            } else {
                if (!audio) {
                    disableRadioButtons();
                }
                menuBarRadioBtn.setVisible(false);
                lstPesquisa.clearSelection();
                index = 20;
            }
        }, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void btnCancelarClick(ActionEvent ev) {
        if (downloadProgressBar.isVisible() || lblResultado1.getText().equals(verifyingDownload + " " + tituloFormatado)
                || downloadProgressBar2.isVisible() || lblResultado2.getText().equals(verifyingDownload + " " + tituloFormatado2)
                || downloadProgressBar3.isVisible() || lblResultado3.getText().equals(verifyingDownload + " " + tituloFormatado3)) {
            btnBaixa.setVisible(true);
            btnBaixa2.setVisible(false);
            btnBaixa3.setVisible(false);
            downloadProgressBar.setVisible(false);
            downloadProgressBar2.setVisible(false);
            downloadProgressBar3.setVisible(false);
            CMD.getProDownloadTitle().destroy();
            pro1.destroy();
            pro2.destroy();
            pro3.destroy();
            CMD.sleep(0.5);
            isBaixando = false;
            isBaixando2 = false;
            isBaixando3 = false;
            btnCancelar.setVisible(false);
        }
    }

    @Override
    protected void itemMenuExitClick(ActionEvent ev) {
        inTheExit();
    }

    @Override
    protected void itemMenuTutorialClick(ActionEvent ev) {
        JOptionPane.showMessageDialog(null, TEXTOS.pegarTexto("joptionpane.tutorial"), "Tutorial", JOptionPane.INFORMATION_MESSAGE, IMAGEM.pegarIcon("/imagens/ytdBanner.png"));
    }

    @Override
    protected void itemMenuSobreClick(ActionEvent ev) {
        JOptionPane.showMessageDialog(null, TEXTOS.pegarTexto("fora.descricao"), TEXTOS.pegarTexto("joptionpane.sobre.titulo"), JOptionPane.INFORMATION_MESSAGE, IMAGEM.pegarIcon("/imagens/ytdBanner.png"));
    }

    protected void inTheExit() {
        ActionEvent ev = null;
        EXECUTOR.shutdown();
        if (pro1.isAlive() || pro2.isAlive() || pro3.isAlive()) {
            Object[] choices = {TEXTOS.pegarTexto("joptionpane.botao.sim"), TEXTOS.pegarTexto("joptionpane.botao.nao")};
            int showConfirmDownloadOnExit = JOptionPane.showOptionDialog(null, TEXTOS.pegarTexto("joptionpane.intheexit"), "",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, IMAGEM.pegarIcon("/imagens/ytdBanner.png"), choices, choices[0]);
            if (showConfirmDownloadOnExit == 1) {
                btnCancelarClick(ev);
                CMD.destruir();
                this.dispose();
                System.exit(0);
            } else {
                CMD.destruir();
                this.dispose();
            }
        } else {
            CMD.destruir();
            this.dispose();
            System.exit(0);
        }
    }

    @Override
    protected void processComponentEvent(ComponentEvent e) {
        
    }

}
