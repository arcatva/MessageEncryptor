FROM golang:1.21-alpine
LABEL authors="zhefu"
WORKDIR /go/src/go-server
COPY . .
RUN go mod download
RUN go build -o /go/bin/go-server
EXPOSE 80
ENTRYPOINT ["/go/bin/go-server"]