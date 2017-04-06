package br.org.multimidia.multimidiavoz.db;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jheimes on 21/06/2016.
 */
public class GenericCRUD<T, K> extends BaseDaoImpl<T, K> {

    private String table;

    /**
     * costrutor padrao
     * @param context contexto da activity
     * @param dataClass class da instancia
     * @param table nome da tabela persistivel
     * @throws SQLException
     */
    public GenericCRUD(Context context, Class<T> dataClass, String table) throws SQLException {

        super(dataClass);
        this.table = table;
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        ConnectionSource cs = databaseHelper.getConnectionSource();
        setConnectionSource(cs);
        initialize();
    }

    /**
     * Criar as entidades
     * @param data entidade generica <T>
     * @return status da persistencia
     */
    @Override
    public int create(T data) {
        int status = 0;
        try {
            status = super.create(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * pesquisa por coluna especifica
     * @param fieldName nome da coluna
     * @param value valor da coluna
     * @return lista <T>
     */
    @Override
    public List<T> queryForEq(String fieldName, Object value) {
        List<T> entyties = new ArrayList<T>();
        try {
            entyties = super.queryForEq(fieldName, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entyties;
    }

    /**
     * Pesquisar por todos os registros
     * @return lista List<T>
     */
    @Override
    public List<T> queryForAll() {
        List<T> entyties = new ArrayList<T>();
        try {
            entyties = super.queryForAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entyties;
    }

    /**
     * Atualiza as entidades
     * @param data entidade generica <T>
     * @return status da persistencia
     */
    @Override
    public int update(T data) {
        int status = 0;
        try {
            status = super.update(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * Atualiza as entidades
     * @param datas entidades generica <T>
     * @return status da persistencia
     */
    public int[] update(List<T> datas) {

        int [] status = new int[datas.size()];

        for(int i = 0 ; i < datas.size() ; i++) {
            try {
//                auditoriumsChange(datas.get(i));
                status[i] = super.update(datas.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return status;
    }

    /**
     * deleta uma coleção de itens
     * @param datas dados Collection<T>
     * @return estado
     */
    public int delete(Collection<T> datas) {
       try {
           return super.delete(datas);
       } catch (Exception e) {
        Log.i("Erro;",e.getMessage());
       }
        return 0;
    }

    @Override
    public int delete(T data) {
        int status = 0;
        try {
            status = super.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    /**
     * retorna o valor maximo da coluna escolhida
     * @return max Valor Coluna
     */
    public long maxValueColumm(String value) {
        long field = 0;
        try {
            field = queryRawValue("select max("+ value +") from "+ table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return field;
    }

    /**
     * retorna o valor total de itens na tabela
     * @return count
     */
    public long count(String value) {
        long field = 0;
        try {
            field = queryRawValue("select count("+ value +") from "+ table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return field;
    }

    /**
     *
     * @param k identificador unico da entidade
     * @return <T>
     */
    @Override
    public T queryForId(K k) {
        try {
            return super.queryForId(k);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * buscar por referencia
     * @param fieldValues parametros por fererencia ROW = VALUE
     * @return List<T>
     */
    @Override
    public List<T> queryForFieldValues(Map<String, Object> fieldValues) {
        List<T> entities = new ArrayList<>();
        try {
            entities = super.queryForFieldValues(fieldValues);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    /**
     * retorna um id para persistilo no banco
     * @return novo id da sequencia
     */
    public long generateId() {
        long field = 0;
        try {
            field = queryRawValue("select max(id) from "+ table);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return field + 1;
    }

    /**
     * pesquisar Entidades
     * @param condition condicao dos parametros 'and' : 'or' ...
     * @param compare variavel de comparacao '=': '<>' : ''
     * @param fieldValues filtro 'coluna:valor'
     * @param orderBy metodo de ordenacao 'nomeColuna'
     * @return lista <T>
     */
    public List<T> queryFor(String condition, String compare, Map<String, Object> fieldValues, String orderBy) {
        String[] args = null;
        String where = "";

        /** metodo padrao*/
        if (condition == null) {
            condition = "and";
        }
        if (compare == null) {
            compare = "=";
        }

        if (fieldValues.entrySet().size() > 0) {
            where += " where 1=1 ";
            args = new String[fieldValues.entrySet().size()];
        }
        int index = 0;
        for (Map.Entry<String, Object> map : fieldValues.entrySet()) {
            where += " "+ condition +" " + map.getKey() + " "+ compare +" ? ";
            args[index] = String.valueOf(map.getValue());
            index++;
        }
        String sql = "SELECT id FROM "+ table +" " + where;

        if (orderBy != null) {
            sql += " order by " + orderBy;
        }
        List<T> entities = new ArrayList<>();
        try {
            GenericRawResults<String[]> raw = queryRaw(sql, args);

            if (raw != null && raw.getNumberColumns() > 0) {
                for (String[] s : raw) {
                    K id = (K) s[0];
                    entities.add(queryForId(id));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    /**
     * obter dados da consulta no formato Map
     * @param query consulta
     * @param params parametros
     * @return HashMap
     */
    public List<HashMap<String, Object>> queryNativeMap(String query, String[] params) {
        List<HashMap<String, Object>> maps = new ArrayList<>();
        try {
            GenericRawResults<String[]> results = queryRaw(query, params);
            List<String[]> result = results.getResults();
            String[] colNames = results.getColumnNames();
            for (int i = 0 ; i < result.size() ; i++) {
                HashMap<String, Object> map = new HashMap<>();
                for (int j = 0 ; j < result.get(i).length ; j++) {
                    map.put(colNames[j], result.get(i)[j]);
                }
                maps.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maps;
    }

    @Override
    public T queryForSameId(T data) {
        T entity = null;
        try {
            entity = super.queryForSameId(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entity;
    }
}
