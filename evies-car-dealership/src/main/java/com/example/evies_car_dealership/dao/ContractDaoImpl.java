@Repository
public class CustomerDaoImpl implements CustomerDao {

    @Override                      // SELECT * WHERE Email=?
    public Optional<Customer> getByEmail(String email) { … }

    @Override                      // INSERT .. RETURN_GENERATED_KEYS
    public int getOrCreate(Customer c) { … }

    @Override                      // UPDATE by Email
    public boolean update(Customer c) { … }

    @Override                      // DELETE by Email
    public boolean deleteByEmail(String email) { … }

    @Override                      // SELECT * FROM Customer
    public List<Customer> getAll() { … }
}
