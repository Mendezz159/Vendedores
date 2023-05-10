/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package vendedores;

import java.util.Scanner;

/**
 *
 * @author HP PORTATIL
 */
public class Vendedores {

    public static class Node {

        int Codigo;
        int Compras;
        int Ganancia;
        String Nombre;
        Node left;
        Node right;

        public Node(int Codigo, String Nombre, int Compras) {
            this.Codigo = Codigo;
            this.Nombre = Nombre;
            this.Compras = Compras;
            this.Ganancia = 0;
            this.left = null;
            this.right = null;
        }
    }

    public static class BinaryTree {

        Node root;

        public BinaryTree() {
            root = null;
        }

        public void IniciarInterfaz() {
            Scanner in = new Scanner(System.in);
            System.out.println("Nombra al vendedor");
            System.out.print("-- ");

            root = new Node(0, in.nextLine(), 0);
            root = InterfazNodo(root, "", 0);
        }

        private Node InterfazNodo(Node node, String Ruta, int nivel) {
            Scanner in = new Scanner(System.in);
            String ThisRuta;
            String Pronombre;

            if (nivel == 0) {
                Pronombre = "Vendedor";
            } else {
                Pronombre = "Cliente";
            }

            var i = true;
            System.out.println("--------------------------------------------------------------------------");
            while (i) {
                ThisRuta = Ruta + "/" + node.Nombre;
                System.out.println("Ruta: " + ThisRuta);
                System.out.println("\n" + Pronombre + ": " + node.Nombre + "\nCodigo: " + node.Codigo);

                if (!(node.right == null && node.left == null)) {
                    System.out.println("Compradores:");
                    if (!(node.right == null)) {
                        System.out.println("\t- " + node.right.Nombre);
                    }
                    if (!(node.left == null)) {
                        System.out.println("\t- " + node.left.Nombre);
                    }
                }

                System.out.println("\nSelecciona alguna de las opcciones ingresando un numero");
                System.out.println("1. Modificar Nombre del " + Pronombre);

                if (nivel == 0) {
                    System.out.println("2. Generar comiciones");
                } else {
                    System.out.println("2. Volver a la anterior ruta");
                }

                System.out.println("3. Ver datos de un comprador");

                if (node.right == null || node.left == null) {
                    System.out.println("4. Añadir nuevo Comprador");
                }

                if (nivel != 0) {
                    System.out.println("5. Añadir una nueva compra");
                }
                System.out.print("-- ");
                int Selection = in.nextInt();
                System.out.println("\n");
                in.nextLine();
                switch (Selection) {
                    case (1):
                        System.out.println("Ingresa el nuevo nombre");
                        System.out.print("-- ");
                        node.Nombre = in.nextLine();
                        break;
                    case (2):
                        i = false;
                        break;
                    case (3):
                        if (node.right == null && node.left == null) {
                            System.out.println("*No hay Compradores*");
                        } else {
                            int numerador = 1;
                            if (!(node.right == null)) {
                                System.out.println(numerador + ". " + node.right.Codigo + "-" + node.right.Nombre);
                                numerador++;
                            }
                            if (!(node.left == null)) {
                                System.out.println(numerador + ". " + node.left.Codigo + "-" + node.left.Nombre);
                            }

                            System.out.println("-- ");
                            int Select = in.nextInt();
                            switch (Select) {
                                case (1):
                                    node.right = InterfazNodo(node.right, ThisRuta, nivel + 1);
                                    break;
                                case (2):
                                    if (numerador == 2) {
                                        node.left = InterfazNodo(node.left, ThisRuta, nivel + 1);
                                    } else {
                                        System.out.println("Valor No valido");
                                    }
                                    break;
                                default:
                                    System.out.println("Valor No valido");
                                    break;
                            }
                        }
                        break;
                    case (4):
                        if (node.right == null || node.left == null) {
                            System.out.println("Ingresa el nombre del nuevo comprador");
                            System.out.print("-- ");
                            String nombre = in.nextLine();

                            System.out.println("Ingresa la cantidad Comprada");
                            System.out.print("-- ");
                            int Compra = in.nextInt();

                            if (!(node.right == null)) {
                                node.left = new Node(GenerarCodigo(node.Codigo, 2), nombre, Compra);
                            } else {
                                node.right = new Node(GenerarCodigo(node.Codigo, 1), nombre, Compra);
                            }
                        } else {
                            System.out.println("Valor No valido");
                        }

                        break;
                    case (5):
                        System.out.println("Ingresa el valor que quieres sumar a la compra");
                        System.out.print("-- ");
                        int compra = in.nextInt();

                        node.Compras += compra;

                        break;
                    default:
                        System.out.println("Valor No valido");
                        break;
                }
            }
            System.out.println("--------------------------------------------------------------------------");
            return node;
        }

        private int GenerarCodigo(int CodigoAnterior, int Clasificacion) {
            int NuevoCodigo = Clasificacion + (CodigoAnterior * 10);
            return NuevoCodigo;
        }

        public void GenerarComiciones() {
            postorderRecursive(root, 0);
        }

        private float postorderRecursive(Node node, int nivel) {

            float Sumatoria = 0;

            float multiplicador = 0;
            if (nivel == 1) {
                multiplicador = (float) 0.1;
            } else {
                multiplicador = (float) 0.01;
            }

            if (node != null && node.left != null) {

                Sumatoria += postorderRecursive(node.right, nivel + 1);
                Sumatoria += postorderRecursive(node.left, nivel + 1);

                System.out.println("La comision de " + node.Nombre + " es de " + Sumatoria);
            }

            if (nivel == 0) {
                return 0;
            }

            return (node.Compras * multiplicador) + Sumatoria;
        }

        public void consoleTree(){
            printTree(this.root,"");
        }
        
        private void printTree(Node node, String prefix) {
            if (node != null) {
                System.out.println(prefix + "|-- " + node.Nombre + " $"+ node.Compras + "  (#" + node.Codigo + ")");
                printTree(node.right, prefix + "    ");
                printTree(node.left, prefix + "    ");
            }
        }
    }

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree();
        Scanner in = new Scanner(System.in);

        System.out.print("1. Quemar datos del grafico\n2. Abrir interfaz\n--");

        int seleccion = in.nextInt();

        switch (seleccion) {
            case (1):
                tree.root = new Node(0, "Alex", 0);
                tree.root.right = new Node(1, "Maria", 10000);
                tree.root.right.right = new Node(11, "Martha", 5000);
                tree.root.right.right.right = new Node(111, "Brayan", 4000);
                tree.root.right.right.right.right = new Node(1111, "Dolores", 30000);
                tree.root.right.right.left = new Node(112, "Mario", 3000);
                tree.root.right.left = new Node(12, "Pedro", 3000);
                tree.root.right.left.right = new Node(121, "Josefa", 2000);
                tree.root.right.left.left = new Node(122, "Natalia", 5000);
                tree.root.left = new Node(2, "Carlos", 30000);
                tree.root.left.right = new Node(21, "Marcos", 1000);
                tree.root.left.left = new Node(22, "Jose", 9000);
                tree.root.left.left.right = new Node(221, "Javier", 2000);
                tree.root.left.left.left = new Node(222, "Manuel", 2000);
                tree.root.left.left.left.right = new Node(2221, "Cristian", 2000);
                tree.root.left.left.left.left = new Node(2222, "Fracisco", 4000);
                break;
            case (2):
                tree.IniciarInterfaz();
                break;
        }
        System.out.print("\n");
        tree.consoleTree();
        System.out.print("\n");
        tree.GenerarComiciones();
    }
}
