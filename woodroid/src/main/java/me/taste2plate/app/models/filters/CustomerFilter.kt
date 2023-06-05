package me.taste2plate.app.models.filters

class CustomerFilter : ListFilter() {

    private lateinit var id: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var role: String

    //all, administrator, editor, author, contributor, subscriber, customer and shop_manager

    fun getEmail(): String {
        return email
    }

    fun getPass(): String {
        return password
    }

    fun setPassword(password: String) {
        this.password = password
        addFilter("password", password)

    }

    fun setUserId(Id: String) {
        this.id = Id
        addFilter("id", id)
    }

    fun setEmail(email: String) {
        this.email = email
        addFilter("email", email)
    }

    fun getRole(): String {
        return role
    }

    fun setRole(role: String) {
        this.role = role
        addFilter("role", role)
    }

    fun setRole(role: Role) {
        this.role = role.toString()
        addFilter("role", role.toString())
    }
}
