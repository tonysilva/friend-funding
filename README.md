#Friend Funding

##Serviços:

###Usuários:

- Criar usuário:  POST: /users
```
{
  "username": "{param}",
  "password": "{param}",
  "profile": {
    "name": {param},
    "email": {param},
    "url": {param}
  }
}

```

- Login:  POST: /login
```
{
  "username": "{param}",
  "password": "{param}"
}

```

###Desejos:

- Criar desejo:  POST: /wishes
```
{
	"name": "{param}",
	"totalValue": "{param}",
  "description": "{param}",
  "url": "{param}",
  "dueDate": "{param}"
}

```
- Resgatar seus desejos: GET: /wishes/mine

- Resgatar desejos dos amigos: GET: /wishes

###Contribuiçes:

- Criar contribuição para desejo: POST: /contributions?wishid={param}
```
{
	"value": "{param}"
}

```
###Social:

- Adicionar amigo: POST: /invitations?profileToId={param}

- Listar convites: GET: /invitations
