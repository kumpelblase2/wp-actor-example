-module(actor).
-export([main/0]).

handle() ->
	receive
		print ->
			io:format("Hello world!"),
			handle();
		die ->
			ok
	end.

main() ->
	spawn(fun handle/0).
