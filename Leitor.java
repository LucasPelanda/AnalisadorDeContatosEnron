import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Leitor {
    public void preencherGrafo(Grafo g) {
        String caminho = "Amostra Enron";

        // Lista os diretorios dentro da pasta que representam os usuarios
        File pastaPrincipal = new File(caminho);
        if (!pastaPrincipal.exists()) {
            System.out.println("Pasta principal não encontrada");
            return;
        }

        File[] pessoas = pastaPrincipal.listFiles();
        for (File pastaPessoa : pessoas) {
            List<File> emails = armazenarEmails(pastaPessoa);

            // remetentesDestinatarios[remetente, destinatario]
            List<String[]> remetentesDestinatarios = new ArrayList<>();
            for (File email : emails) {
                String[] remetenteDestinatario = lerEmail(email);
                remetentesDestinatarios.add(remetenteDestinatario);
            }

            for (String[] remetenteDestinatario : remetentesDestinatarios) {
                if (remetenteDestinatario.length == 0 || remetenteDestinatario[0].isEmpty()
                        || remetenteDestinatario[1].isEmpty())
                    continue;
                g.adicionarMensagem(remetenteDestinatario[0], remetenteDestinatario[1]);
            }
        }
    }

    /**
     * Entra no diretório sent do usuario e armazena os arquivos dentro dele
     * 
     * @param pastaPessoa
     * @return apenas os arquivos dentro da pasta sent
     */
    public List<File> armazenarEmails(File pastaPessoa) {
        List<File> emailsFiltrados = new ArrayList<>();
        if (pastaPessoa == null || !pastaPessoa.isDirectory()) {
            return emailsFiltrados;
        }

        File pastaSent = new File(pastaPessoa, "sent");
        if (!pastaSent.exists() || !pastaSent.isDirectory()) {
            return emailsFiltrados;
        }

        File[] emails = pastaSent.listFiles();
        for (File email : emails) {
            if (email.isFile()) {
                emailsFiltrados.add(email);
            }
        }
        return emailsFiltrados;
    }

    public String[] lerEmail(File email) {
        List<String> linhas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(email))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhas.add(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String remetente = "";
        String destinatario = "";
        for (String linha : linhas) {
            if (!remetente.isEmpty() && !destinatario.isEmpty()) {
                break;
            }

            if (linha.contains("From: ") && linha.contains("@")) {
                remetente = linha.split(" ")[1];
            } else if (linha.contains("To: ") && linha.contains("@")) {
                destinatario = linha.split(" ")[1];
            }
        }

        if (remetente.isEmpty() || destinatario.isEmpty()) {
            return new String[0];        }
        
        String[] remetenteDestinatario = { remetente, destinatario };
        return remetenteDestinatario;
    }
}
