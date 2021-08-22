# hackaton-tracker

## Installation

```zsh
# https://clojure.org/guides/getting_started
curl -O https://download.clojure.org/install/linux-install-1.10.3.943.sh
chmod +x linux-install-1.10.3.943.sh
sudo ./linux-install-1.10.3.943.sh

yay -S rlwrap jdk11-openjdk --noconfirm


```

## Usage

```zsh 
git clone git@github.com:Panthevm/hackaton-tracker.git
cd hackaton-tracker
clj -m hack.core
```

## Or

```zsh
java -cp target/hack.jar clojure.main -m hack.core  
```
