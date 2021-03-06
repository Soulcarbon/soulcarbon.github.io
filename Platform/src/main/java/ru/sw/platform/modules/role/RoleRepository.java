package ru.sw.platform.modules.role;

import org.springframework.stereotype.Repository;
import ru.sw.platform.core.repositories.AbstractRepository;

import java.util.List;

@Repository(value = "RoleRepository")
public class RoleRepository extends AbstractRepository<Role> {
}
