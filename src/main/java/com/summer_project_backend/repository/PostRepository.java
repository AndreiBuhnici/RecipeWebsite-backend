package com.summer_project_backend.repository;


import com.summer_project_backend.model.Ingredient;
import com.summer_project_backend.model.Post;
import com.summer_project_backend.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    @NonNull
    Page<Post> findAllByUserId(@NonNull UUID userId, @NonNull Pageable pageable);

    @NonNull
    Page<Post> findAllByUserIdAndTitleContains(@NonNull UUID userId, String title,
                                                 @NonNull Pageable pageable);

    @NonNull
    @Query("select p from Post p join p.ingredients ing join p.tags tag " +
            "where p.user.id = ?1 and (p.title like %?2% or ?2 is null)" +
            "and (tag.name in ?3 or ?3 is null) and (ing.name in ?4 or ?4 is null)")
    Page<Post> findAllByUserIdAndTitleAndTagsAndIngredients(@Param("userid") @NonNull UUID userId,
                                                            @Param("title") String title,
                                                            @Param("tags") List<String> tags,
                                                            @Param("ingredients") List<String> ingredients,
                                                            @NonNull Pageable pageable);
}
