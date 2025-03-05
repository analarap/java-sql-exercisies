import java.util.*;

public class LaserSculpture {
    public static int contarLigamentosLaser(int altura, int comprimento, int[] cortes) {
        int ligamentos = 0;
        int nivelAtual = altura;

        for (int i = 0; i < comprimento; i++) {
            if (cortes[i] < nivelAtual) {
                ligamentos++;
            }
            nivelAtual = cortes[i];
        }

        return ligamentos;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            int altura = scanner.nextInt();
            int comprimento = scanner.nextInt();

            if (altura == 0 && comprimento == 0) {
                break;
            }

            int[] cortes = new int[comprimento];
            for (int i = 0; i < comprimento; i++) {
                cortes[i] = scanner.nextInt();
            }
            System.out.println(contarLigamentosLaser(altura, comprimento, cortes));
        }

        scanner.close();
    }
}
