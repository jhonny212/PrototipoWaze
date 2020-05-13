package EstructuraArbolB;

public class NodoB {

    Nodo[] datos;
    NodoB padre;
    int size;
    final int NoNodo;

    public NodoB(int NoNodo) {
        this.padre = null;
        this.datos = new Nodo[5];
        this.size = 0;
        this.NoNodo = NoNodo;

    }

    public int getSize() {
        return size;
    }

    //Metodo recursivo para buscar posicion a insertar el nodo
    public void buscarNodo(NodoB aux, ruta rta) {
        double clave1 = rta.getClave();
        if (aux.size == 0) {
            aux.agregarDato(rta);
            return;
        }
        for (int i = 0; i < aux.size; i++) {
            Nodo tmp = aux.datos[i];
            double clave2 = tmp.getDato().getClave();

            if (clave1 <= clave2) {
                if (aux.datos[i].hijoIzq != null) {
                    aux.datos[i].hijoIzq.buscarNodo(aux.datos[i].hijoIzq, rta);
                    break;
                } else {
                    aux.agregarDato(rta);
                    break;

                }
            }
            if (aux.size == i + 1) {
                if (aux.datos[i].hijoDer != null) {
                    aux.datos[i].hijoDer.buscarNodo(aux.datos[i].hijoDer, rta);
                    break;
                } else {
                    aux.agregarDato(rta);
                    break;
                }
            }

        }
    }

    private void agregarDato(ruta rta) {
        if (size < 4) {
            add(rta);

        } else {
            add(rta);

            dividir(this);

        }
    }

    private void add(ruta rta) {
        this.size++;
        this.datos[size - 1] = new Nodo(rta, size);
        ordenarDatos(true);
    }

    private void ordenarDatos(boolean v) {
        for (int i = 0; i < size; i++) {
            Nodo tmp = this.datos[i];
            for (int j = i + 1; j < size; j++) {
                try {
                    Nodo t2 = this.datos[j];
                    if (tmp.getDato().getClave() > t2.getDato().getClave()) {
                        if (v) {
                            NodoB aux = this.datos[i].hijoDer;
                            NodoB aux2 = this.datos[i].hijoIzq;
                            this.datos[i].hijoDer = this.datos[j].hijoDer;
                            this.datos[i].hijoIzq = this.datos[j].hijoIzq;
                            this.datos[j].hijoDer = aux;
                            this.datos[j].hijoIzq = aux2;
                        }
                        {
                            ruta t = tmp.getDato();
                            this.datos[i].setDato(t2.getDato());
                            this.datos[j].setDato(t);
                        }

                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                }
            }
        }
    }

    private void dividir(NodoB nd) {
        if (nd.padre == null) {
            ArbolB.contador++;
            nd.padre = new NodoB(ArbolB.contador);
        }
        ruta ins = nd.datos[2].getDato();
        nd.padre.add(ins);
        ArbolB.contador++;
        NodoB nuevo = new NodoB(ArbolB.contador);
        nuevo.add(nd.datos[3]);
        nuevo.add(nd.datos[4]);
        int pos = nd.getPos(ins, nd.padre);
        nd.padre.datos[pos].hijoDer = nuevo;
        nd.datos[2] = null;
        nd.datos[3] = null;
        nd.datos[4] = null;
        nd.size = 2;
        nd.padre.datos[pos].hijoIzq = nd;

        try {
            NodoB aux2 = nd.padre.datos[pos].hijoDer;
            nd.padre.datos[pos + 1].hijoIzq = aux2;
            nd.padre.datos[pos].hijoDer = null;
        } catch (Exception e) {
        }
        NodoB puntero = nd;
        while (puntero != null) {
            if (puntero.padre == null) {
                break;
            } else {
                puntero = puntero.padre;
            }
        }
        arreglarPuntero(puntero);
        if (nd.padre.size == 5) {
            NodoB punt = nd.padre.datos[2].hijoIzq;
            ruta valor = nd.padre.datos[2].getDato();
            nd.padre.dividir(nd.padre);
            int posP = getPos(valor, nd.padre.padre);
            nd.padre.padre.datos[posP].hijoIzq.datos[nd.padre.padre.datos[posP].hijoIzq.size - 1].hijoDer = punt;

        }
    }

    private void arreglarPuntero(NodoB aux) {
        if (aux == null) {
            return;
        }
        for (int i = 0; i < aux.size; i++) {
            try {
                Nodo tmp = aux.datos[i];
                if (tmp != null) {
                    if (tmp.hijoIzq != null) {
                        tmp.hijoIzq.padre = aux;
                        arreglarPuntero(tmp.hijoIzq);

                    }
                    //if(i==aux.size-1){
                    if (tmp.hijoDer != null) {
                        if (i == aux.size - 1) {
                            tmp.hijoDer.padre = aux;
                            arreglarPuntero(tmp.hijoDer);
                        } else {
                            tmp.hijoDer = null;
                        }

                    }
                } else {
                    break;
                }
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }

        }
    }

    private void add(Nodo n) {
        this.size++;
        this.datos[this.size - 1] = n;
        this.datos[this.size - 1].key = size;
    }

    private int getPos(ruta ruta, NodoB aux) {
        for (int i = 0; i < aux.size; i++) {
            if (ruta.getClave() == aux.datos[i].getDato().getClave()) {
                return i;
            }
        }
        return 0;
    }

    void eliminar(NodoB aux, double clave1, int posPadre) {
        for (int i = 0; i < aux.size; i++) {
            Nodo tmp = aux.datos[i];
            double clave2 = tmp.getDato().getClave();
            if (clave1 == clave2) {
                //posicion del nodo,posicion del padre, ref donde se encuentra el dato
                aux.eliminar(i, posPadre, aux, i);
                break;
            }
            if (clave1 < clave2) {
                if (aux.datos[i].hijoIzq != null) {
                    aux.datos[i].hijoIzq.eliminar(aux.datos[i].hijoIzq, clave1, i);
                    if (aux.size < 2) {
                        if (aux.padre != null) {
                            aux.unir2(i, posPadre, aux);
                        }
                    }
                }
                break;
            }
            if (aux.size == i + 1) {
                if (aux.datos[i].hijoDer != null) {
                    aux.datos[i].hijoDer.eliminar(aux.datos[i].hijoDer, clave1, i);
                }
                break;
            }
        }
    }

    private void eliminar(int pos, int posPadre, NodoB punt, int finalDir) {
        int tamaño = punt.size;
        if (pos != tamaño - 1) {
            if (punt.datos[pos + 1].hijoIzq == null) {
                boolean value = fix(pos, punt);
                if (value) {
                    punt.prestar(punt.padre, posPadre, finalDir, posPadre, punt);
                } else {

                }
            }
        } else {
            if (punt.datos[pos].hijoIzq == null && punt.datos[pos].hijoDer == null) {
                boolean value = fix(pos, punt);
                if (value) {
                    punt.prestar(punt.padre, posPadre, finalDir, posPadre, punt);
                } else {

                }
            }
        }
    }

    private boolean fix(int i, NodoB punt) {
        System.out.println("this--------" + punt.datos[0].getDato().getClave());
        for (int j = i; j < punt.size; j++) {
            try {
                try {
                    punt.datos[j + 1].key = punt.datos[j + 1].key - 1;
                } catch (NullPointerException ex) {
                }

                punt.datos[j] = punt.datos[j + 1];

            } catch (ArrayIndexOutOfBoundsException e) {

            }
        }
        punt.size -= 1;
        return punt.size < 2;
    }

    private void prestar(NodoB aux, int pos, int dir, int posPadre, NodoB href) {

        if (pos < aux.size - 1) {
            if (aux.datos[pos + 1].hijoIzq.size <= 2) {
                prestar(aux, pos + 1, dir, posPadre, href);
            } else {

            }
        } else {

            try {
                if (aux.datos[pos].hijoIzq != null && aux.datos[pos].hijoDer != null) {
                    if (aux.datos[pos].hijoIzq.size > 2) {

                    } else {
                        System.out.println("uniendo");
                        unir(dir, posPadre, aux, href);

                    }
                }

            } catch (NullPointerException e) {
            }

        }

    }

    private void unir(int pos, int posPadre, NodoB punt, NodoB href) {
        try {
            //Obteniendo el dato en (0) donde se elimino
            ruta rta = href.datos[pos].getDato();
            //asignando el valor a su siguiente hermano
            punt.datos[posPadre + 1].hijoIzq.add(rta);
            //obteniendo el valor intermedio entre su hermano y este (padre) y asignarlo
            rta = punt.datos[posPadre].getDato();
            punt.datos[posPadre + 1].hijoIzq.add(rta);
            //mover <- para atras los nodos del padre
            punt.fix(posPadre, punt);
            //configurar punteros
            arreglarPuntero(punt.padre);

        } catch (NullPointerException e) {
        }

    }

    private void unir2(int pos, int posPadre, NodoB punt) {
        ruta rta = punt.datos[pos].getDato();
        punt.padre.datos[posPadre + 1].hijoIzq.add(rta);
        int posi = getPos(rta, punt.padre.datos[posPadre + 1].hijoIzq);
        punt.padre.datos[posPadre + 1].hijoIzq.datos[posi] = punt.datos[pos];
        rta = punt.padre.datos[posPadre].getDato();
        punt.padre.datos[posPadre + 1].hijoIzq.add(rta);
        punt.padre.fix(posPadre, punt.padre);
        punt.padre.datos[0].hijoIzq.datos[posi + 1].hijoIzq = punt.padre.datos[0].hijoIzq.datos[posi].hijoDer;
        punt.padre.datos[0].hijoIzq.datos[posi].hijoDer = null;
        punt.padre.datos[0].hijoIzq.datos[posi].key = posi + 1;
        arreglarPuntero(punt.padre.padre);

    }

}
