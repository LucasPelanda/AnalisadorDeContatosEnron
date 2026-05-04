import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

            for(File email : emails){
                List<String> remetenteDestinatario = lerEmail(email);
                if(remetenteDestinatario.size() == 0){
                    continue;
                }
                String remetente = remetenteDestinatario.get(0);
                for(int i = 1; i < remetenteDestinatario.size(); i++){
                    String destinatario = remetenteDestinatario.get(i);
                    g.adicionarMensagem(remetente, destinatario);
                }
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

    /**
     * 
     * @param email arquivo de texto que contem o email
     * @return {remetete, destinatario1, destinatario2, ...}
     */
    public List<String> lerEmail(File email){
        List<String> linhas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(email))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhas.add(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> resultado = new ArrayList<>();

        if(!linhas.get(2).contains("From") || !linhas.get(3).contains("To")){
            // System.out.println("Verificar: " + email.getAbsolutePath());
            return new ArrayList<>();
        }
        
        resultado.add(linhas.get(2).substring(5).trim());
        String[] destinatarios = linhas.get(3).substring(3).replace(" ", "").split(",");

        for(String destinatario : destinatarios){
            resultado.add(destinatario);
        }

        int i = 4;
        while(!linhas.get(i).contains("Subject") && !linhas.get(i).contains("Mime-Version") && !linhas.get(i).contains("Content-Type")){
            for(String destinatario : linhas.get(i).substring(1).replace(" ", "").split(",")){
                resultado.add(destinatario);
            }
            i++;
        }

        if(resultado.size() <= 1){
            // System.out.println("Verificar: " + email.getAbsolutePath());
            return new ArrayList<>();
        }
        return resultado;
    }
}
