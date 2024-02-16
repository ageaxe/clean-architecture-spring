package com.example.gateway;

import com.example.dbentity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface InventoryJPARepository extends JpaRepository<Inventory, UUID>, JpaSpecificationExecutor<Inventory> {
    @Query(value="SELECT * FROM inventory " +
            "WHERE " +
            "client_app = ?1 LIMIT 1", nativeQuery = true)
    Inventory checkIfInventoryExists(String clientApp);
}
