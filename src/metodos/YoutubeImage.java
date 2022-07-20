package metodos;

import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

public class YoutubeImage {
	private URL url;
	private ImageIcon icon;
	private Image image;

	/**
	 * Retorna um ImageIcon
	 * @param urlIcon (String) - Caminho completo do ícone.
	 * @return icon
	 */
	public ImageIcon pegarIcon(String urlIcon) {
		url = getClass().getResource(urlIcon);
		icon = new ImageIcon(url);

		return icon;
	}

	/**
	 * Retorna uma Image
	 * @param urlImage (String) - Caminho completo da imagem;
	 * @return image
	 */
	public Image pegarImage(String urlImage) {
		url = getClass().getResource(urlImage);
		icon = new ImageIcon(url);
		image = icon.getImage();

		return image;
	}
}
