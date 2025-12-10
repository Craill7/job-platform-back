UPDATE sys_menu 
SET path = 'tagCategories',                    
    component = 'recruit/tagCategories/index', 
    perms = 'recruit:tagCategories:list'       
WHERE menu_id = 2040;

UPDATE sys_menu 
SET perms = REPLACE(perms, 'recruit:categories:', 'recruit:tagCategories:')
WHERE parent_id = 2040;