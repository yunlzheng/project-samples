Spring Cloud Kubernetes Sample
==========

## Ingress

```
$ k create -f sample-ingress.yaml
```

```
$ k get ingress
NAME       HOSTS                        ADDRESS         PORTS     AGE
kube-app   kube-app.kubernetes101.com   39.96.133.114   80        18s
```

```
curl -H "Host: kube-app.kubernetes101.com" http://39.96.133.114
```