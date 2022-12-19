import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Menus {
    private static final int logins = 0, senhas = 1, nomes = 2;
    private static Scanner sc = new Scanner(System.in);
    private static ArrayList<ArrayList<String>> base = new ArrayList<>();
    private static ArrayList<ArrayList<String>> basePosts = new ArrayList<>();

    static {
        base.add(new ArrayList<>());
        base.add(new ArrayList<>());
        base.add(new ArrayList<>());
    }

    public static void welcome() {
        String menu = "cef", option;
        System.out.println("\n=====Bem vindo a Rede Social=====");
        System.out.println("=== O que você deseja fazer? ===");
        System.out.println("    === C -> Cadastro ===");
        System.out.println("    === E -> Login  =====");
        System.out.println("    === F -> Sair   =====");
        System.out.println(" Digite sua opção: ");
        option = sc.nextLine().toLowerCase();
        while (!menu.contains(option) || option.isEmpty() ||option.isBlank()) {
            System.out.println(" ! Digite uma opção válida ! ");
            option = sc.nextLine().toLowerCase();
        }
        if (option.equals("f")) {
            System.out.println("Até a próxima !!!");
        } else if (option.equals("c")) {
            menuRegister();
            welcome();
        } else {
            menuLogin();
            welcome();
        }
    }

    public static void menuRegister() {
        String login, name, password;
        System.out.println("\n###_ Menu cadastro _###");
        login = textValidation("Digite seu Login: ", "Digite novamente sem deixar espaços em branco ou vazio! ");
        if (loginExist(login)) {
            System.out.println("\nEste login já existe, digite um novo: ");
            menuRegister();
        } else {
            name = textValidation("Digite seu Nome: ", "Seu nome não pode ser em branco ou vazio! ");
            password = textValidation("Digite sua Senha: ", "Sua senha não pode ser vazia ou em branco! ");
            addUser(login, name, password);
        }
    }

    private static void addUser(String login, String name, String password) {
        base.get(logins).add(login);
        base.get(senhas).add(password);
        base.get(nomes).add(name);
        basePosts.add(new ArrayList<>());
    }

    public static boolean loginExist(String login) {
        for (var l : base.get(logins)) {
            if (l.equals(login)) return true;
        }
        return false;
    }

    public static String textValidation(String msg, String msgError) {
        String txt;
        System.out.println(msg);
        txt = sc.nextLine();
        while (txt.isEmpty() || txt.isBlank()) {
            System.out.println(msgError);
            System.out.println(msg);
            txt = sc.nextLine();
        }
        return txt;
    }

    public static void menuLogin() {
        String log, pass;
        int aux = 0;
        Boolean exist = false;
        System.out.println("\n###_ Menu Login _###");
        System.out.println("Digite seu Login: ");
        log = sc.nextLine();
        for (String n : base.get(logins)) {
            if (n.equals(log)) {
                exist = true;
                System.out.println("Digite sua senha: ");
                pass = sc.nextLine();
                boolean passOk = pass.equals(base.get(senhas).get(aux));
                while (!passOk) {
                    System.out.println("Senha errada, digite novamente ou digite 'S' para sair: ");
                    pass = sc.nextLine();
                    passOk = pass.equals(base.get(senhas).get(aux));
                    if (pass.toLowerCase().equals("s")) {
                        System.out.println("Saindo do menu... ");
                        return;
                    } else if (passOk) {
                        break;
                    }
                }
                logedUser(aux);
            }
            aux++;
        }
        if (base.get(logins).size() == 0) {
            System.out.println("\nRealize um novo cadastro !");
        } else if (!exist) {
            System.out.println("Login não encontrado, realize seu cadastro !!");
        }
    }

    public static void logedUser(int posicao) {
        String user = base.get(nomes).get(posicao), menuLoged = "ptf", optionLoged;
        System.out.println("\n===== Bem vindo a Rede Social " + user + " !!! ======");
        System.out.println("=== O que você deseja fazer? ===");
        System.out.println("    === P -> Nova postagem ===");
        System.out.println("    === T -> Visualizar timeline  =====");
        System.out.println("    === F -> Realizar Logoff   =====");
        System.out.println("    Digite sua opção: ");
        optionLoged = sc.nextLine().toLowerCase();
        while (!menuLoged.contains(optionLoged)) {
            System.out.println("Digite uma opção válida: ");
            optionLoged = sc.nextLine().toLowerCase();
        }
        switch (optionLoged) {
            case "f":
                System.out.println("\nLogoff realizado com sucesso !!!");
                break;
            case "p":
                menuPost(posicao);
                logedUser(posicao);
                break;
            default:
                menuTimeline(posicao);
        }

    }

    public static void menuPost(int pos) {
        ArrayList<String> posts = basePosts.get(pos);
        boolean opt = true;
        while (opt) {
            System.out.println("Digite sua mensagem: ");
            String tex = sc.nextLine();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            posts.add(dtf.format(now) + " | " + tex);
            System.out.println(posts.get(posts.size() - 1));
            System.out.println("Deseja realizar uma nova postagem ? S- Sim ou N - Não");
            tex = sc.nextLine();
            if (!tex.equalsIgnoreCase("s")) {
                opt = false;
            }
        }
    }

    public static void menuTimeline(int pos) {
        System.out.println("\n############################");
        System.out.println("Suas postagens: ");
        for (String n : basePosts.get(pos)) {
            System.out.println(n);
        }
        logedUser(pos);
    }
}