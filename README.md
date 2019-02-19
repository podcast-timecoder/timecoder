create database timecoder;

select *
from timecoder.episode e join timecoder.theme t on t.episode_id = e.id
order by episode_id, `timestamp` desc

select *
from
episode

select *
from theme

select *
from hibernate_sequence

insert into episode (
    id,
    is_started,
    name)
values (
    56,
    false,
    "Привет");

CREATE DATABASE timecoder CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

JaCoCo

Dump
----
java -jar jacococli.jar dump --port=36320 --address=localhost --destfile=result.exec
----

Generate report

----
java -jar jacococli.jar report result.exec --classfiles=../build/classes --sourcefiles=../src/main/java --html report
----

