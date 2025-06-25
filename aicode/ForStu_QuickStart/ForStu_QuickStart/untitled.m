
clc;
clear;
x = 1:1:100000;
y=1/x*x-1/x*x*x*x;
xlabel('X(m)')
ylabel('Y(m)')
plot(x,y)