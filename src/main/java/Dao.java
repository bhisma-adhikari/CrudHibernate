public interface Dao {
    // Return entity if exists in database, else return null
    public BaseEntity getById(Integer id);

//    public List<BaseEntity> getAll();

    // Return the id of saved entity
    // Return null if operation fails
    public Integer add(BaseEntity entity);

    // If such (given by id) entity exists in database, try to update it. If successful, return true else return false
    // If such entity does not exist in database, return null
    public Boolean update(Integer id, BaseEntity entity);

    // If an object with given id exists in database, try to delete it. If successful, return true else return false
    // If such entity does not exist, return null
    public Boolean delete(Integer id);
}
